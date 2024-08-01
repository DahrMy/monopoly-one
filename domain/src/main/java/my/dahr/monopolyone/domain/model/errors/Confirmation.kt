package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class Confirmation(
    override val code: Int = 11
) : WrongReturnable