package my.dahr.monopolyone.data.repository

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES
import my.dahr.monopolyone.utils.currentTimeInSec
import retrofit2.Callback
import javax.inject.Inject

class SessionRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: AuthorizationApi
) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    var session: Session?
        get() {
            val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
            return Gson().fromJson(sessionJson, Session::class.java)
        }
        set(value) {
            val serializedData = Gson().toJson(value)
            sharedPreferences.edit()
                .putString(SESSION_KEY, serializedData)
                .apply()
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

}



