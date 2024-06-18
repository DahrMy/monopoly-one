package my.dahr.monopolyone.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.api.IpApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class SessionHelper @Inject constructor(
    @ApplicationContext context: Context,
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

    suspend inline fun safeUse(liveData: MutableLiveData<RequestStatus>, predicate: () -> Unit) {

        val callback: () -> MonopolyCallback<SessionResponse> = {
            object : MonopolyCallback<SessionResponse>(liveData) {
                override fun onSuccessfulResponse(
                    call: Call<SessionResponse>, responseBody: SessionResponse
                ) {
                    session = responseBody.data.toSession()
                    super.onSuccessfulResponse(call, responseBody)
                }
            }
        }

        if (isCurrentIpChanged()) {
            refreshSavedIp()
            session?.let { refreshSession(it.refreshToken, callback) }
        }

        if (isSessionNotExpired()) {
            predicate.invoke()
        } else {
            if (session != null) {
                refreshSession(session!!.refreshToken, callback)
                predicate.invoke()
            } else {
                liveData.postValue(RequestStatus.AuthorizationError)
            }
        }

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

    fun refreshSession(refreshToken: String, callback: () -> Callback<SessionResponse>) {
        val requestBody = "refresh_token" to refreshToken
        val call = api.authRefreshRequest(requestBody)
        call.enqueue(callback.invoke())
    }

    fun removeSession() { session = null }

    suspend fun refreshSavedIp() {
        coroutineScope {
            val currentIp = async { ipApi.getMyIp().ip }
            savedIp = currentIp.await()
        }
    }

    suspend fun isCurrentIpChanged(): Boolean = coroutineScope {
        val currentIp = async { ipApi.getMyIp().ip }
        return@coroutineScope currentIp.await() == savedIp
    }
}