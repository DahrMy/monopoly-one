package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class ObjectsLimitError(
    override val code: Int = 6
) : WrongReturnable
