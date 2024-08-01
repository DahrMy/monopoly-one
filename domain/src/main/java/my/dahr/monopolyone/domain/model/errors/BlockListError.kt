package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class BlockListError(
    override val code: Int = 107
) : WrongReturnable