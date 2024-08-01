package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class IncompatibleCraftError(
    override val code: Int = 605
) : WrongReturnable