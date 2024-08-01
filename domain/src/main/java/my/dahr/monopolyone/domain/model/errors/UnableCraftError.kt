package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class UnableCraftError(
    override val code: Int = 603
) : WrongReturnable