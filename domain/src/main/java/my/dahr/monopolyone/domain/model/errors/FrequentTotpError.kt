package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class FrequentTotpError(
    override val code: Int = 414
) : WrongReturnable