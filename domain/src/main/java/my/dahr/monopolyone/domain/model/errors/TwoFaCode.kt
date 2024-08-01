package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class TwoFaCode(
    override val code: Int = 12
) : WrongReturnable