package my.dahr.monopolyone.ui.login

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.api.MonopolyApi
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES
import retrofit2.Callback
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val api: MonopolyApi
) {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun postSignIn(email: String, password: String, callback: Callback<SessionResponse>) {
        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )
        val call = api.authSignInRequest(requestBody)
        call.enqueue(callback)
    }

    fun saveSession(session: Session) {
        val serializedData = Gson().toJson(session)
        sharedPreferences.edit()
            .putString(SESSION_KEY, serializedData)
            .apply()

        Log.i("SESSION", "Session saved with data:\n$serializedData")
    }

}