package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class ChatRestrictionError(
    override val code: Int = 1203
) : WrongReturnable