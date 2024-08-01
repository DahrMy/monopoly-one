package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class UndefinedRequestError(
    override val code: Int
) : WrongReturnable
