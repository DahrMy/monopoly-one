package my.dahr.monopolyone.ui.login

import dagger.hilt.android.scopes.ViewModelScoped
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.response.LoginBaseResponse
import retrofit2.Callback
import javax.inject.Inject

@ViewModelScoped
class LoginRepository @Inject constructor(
    private val api: AuthorizationApi
) {

    fun postSignIn(email: String, password: String, callback: () -> Callback<LoginBaseResponse>) {
        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )
        val call = api.authSignInRequest(requestBody)
        call.enqueue(callback.invoke())
    }

}