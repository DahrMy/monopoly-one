package my.dahr.monopolyone.data.source.auth.remote.dto.request

internal data class AuthSignInRequest(
    private val email: String,
    private val password: String
)
