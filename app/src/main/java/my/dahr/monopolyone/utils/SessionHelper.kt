package my.dahr.monopolyone.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import my.dahr.monopolyone.data.SHARED_PREFERENCES
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.api.IpApi
import my.dahr.monopolyone.data.network.api.monopoly.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.workers.SessionWorker
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class SessionHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: AuthorizationApi,
    private val ipApi: IpApi
) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    var session: Session?
        get() {
            val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
            return Gson().fromJson(sessionJson, Session::class.java)
        }
        set(value) {
            if (value != null) {
                val serializedData = Gson().toJson(value)
                sharedPreferences.edit()
                    .putString(SESSION_KEY, serializedData)
                    .apply()
            } else {
                sharedPreferences.edit().remove(SESSION_KEY).apply()
            }
        }

    var savedIp: String?
        get() = sharedPreferences.getString(IP_KEY, "")
        private set(value) { sharedPreferences.edit().putString(IP_KEY, value).apply() }

    suspend fun safeUse(
        liveData: MutableLiveData<RequestStatus>,
        predicate: (Session) -> Unit
    ) {
        if (haveInternetConnection()) {
            val callback = createCallback(liveData, flow = null)
            useSession(
                flow = null,
                liveData = liveData,
                callback = callback,
                predicate = predicate
            )
        } else {
            liveData.postValue(RequestStatus.NoInternetConnection)
        }
    }

    suspend fun safeUse(
        flow: MutableSharedFlow<RequestStatus>,
        predicate: (Session) -> Unit
    ) {
        if (haveInternetConnection()) {
            val callback = createCallback(liveData = null, flow)
            useSession(
                flow = flow,
                liveData = null,
                callback = callback,
                predicate = predicate
            )
        } else {
            flow.emit(RequestStatus.NoInternetConnection)
        }
    }

    fun refreshSession(
        refreshToken: String,
        callback: () -> Callback<SessionResponse>
    ) {
        val requestBody = "refresh_token" to refreshToken
//        val call = api.authRefreshRequest(requestBody)
//        call.enqueue(callback.invoke())
//
//        session?.let { SessionWorker.enqueue(it.expiresAt, context) }
    }

    fun removeSession() { session = null }

    suspend fun refreshSavedIp() {
//        coroutineScope {
//            val currentIp = async { ipApi.getMyIp().ip }
//            savedIp = currentIp.await()
//        }
    }

    fun isSessionNotExpired(): Boolean {
        if (session != null) {
            val isNotExpired = (session!!.expiresAt - currentTimeInSec) >= 10 // Ten seconds for reserve
            if (!isNotExpired) {
                sharedPreferences.edit().remove(SESSION_KEY).apply()
            }
            return isNotExpired
        } else {
            return false
        }
    }

    suspend fun isCurrentIpChanged(): Boolean = coroutineScope {
//        val currentIp = async { ipApi.getMyIp().ip }
//        return@coroutineScope currentIp.await() == savedIp
        return@coroutineScope true
    }

    fun haveInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun createCallback(
        liveData: MutableLiveData<RequestStatus>?,
        flow: MutableSharedFlow<RequestStatus>?
    ): () -> MonopolyCallback<SessionResponse> = {

        object : MonopolyCallback<SessionResponse>(liveData, flow) {
            override fun onSuccessfulResponse(
                call: Call<SessionResponse>, responseBody: SessionResponse
            ) {
                session = responseBody.data.toSession()
                super.onSuccessfulResponse(call, responseBody)
            }
        }

    }

    private suspend fun useSession(
        liveData: MutableLiveData<RequestStatus>?,
        flow: MutableSharedFlow<RequestStatus>?,
        callback: () -> MonopolyCallback<SessionResponse>,
        predicate: (Session) -> Unit
    ) {
        if (isCurrentIpChanged()) {
            refreshSavedIp()
            session?.let { refreshSession(it.refreshToken, callback) }
        }

        if (isSessionNotExpired() && session != null) {
            predicate.invoke(session!!)
        } else if (session != null) {
            refreshSession(session!!.refreshToken, callback)
            predicate.invoke(session!!)
        } else {
            liveData?.postValue(RequestStatus.AuthorizationError)
            flow?.emit(RequestStatus.AuthorizationError)
        }
    }

}