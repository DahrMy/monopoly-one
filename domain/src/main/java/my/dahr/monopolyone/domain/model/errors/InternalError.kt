package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class InternalError(
    override val code: Int = 3
) : WrongReturnable
