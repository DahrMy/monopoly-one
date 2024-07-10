package my.dahr.monopolyone.data.network.dto.request.monopoly.auth

data class AuthSignInRequest(
    private val email: String,
    private val password: String
)
