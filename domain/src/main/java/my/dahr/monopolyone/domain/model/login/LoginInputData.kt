package my.dahr.monopolyone.domain.model.login

import my.dahr.monopolyone.domain.usecase.login.SignInUseCase

/**
 * A model that is using for [SignInUseCase].
 */
data class LoginInputData(
    val email: String,
    val password: String
)
