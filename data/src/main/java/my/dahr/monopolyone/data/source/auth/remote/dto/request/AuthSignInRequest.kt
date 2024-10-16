package my.dahr.monopolyone.data.source.auth.remote.dto.request

import com.google.gson.annotations.Expose

data class AuthSignInRequest(
    @Expose
    private val email: String,
    @Expose
    private val password: String
)
