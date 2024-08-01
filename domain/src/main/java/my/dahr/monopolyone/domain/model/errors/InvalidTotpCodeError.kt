package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class InvalidTotpCodeError(
    override val code: Int = 413
) : WrongReturnable