package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class RequestsLimitError(
    override val code: Int = 7
) : WrongReturnable
