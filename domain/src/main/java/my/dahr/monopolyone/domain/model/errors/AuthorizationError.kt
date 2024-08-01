package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class AuthorizationError(
    override val code: Int = 1,
    val description: String
) : WrongReturnable