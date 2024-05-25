package my.dahr.monopolyone.utils

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.api.IpApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
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