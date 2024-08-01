package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class ItemUsingOperationError(
    override val code: Int = 624
) : WrongReturnable