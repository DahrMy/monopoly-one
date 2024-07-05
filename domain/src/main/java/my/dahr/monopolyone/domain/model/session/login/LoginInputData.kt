package my.dahr.monopolyone.domain.model.session.login

/**
 * A model that is using for [SignInUseCase][my.dahr.monopolyone.domain.usecase.login.SignInUseCase].
 */
data class LoginInputData(
    val email: String,
    val password: String
)
