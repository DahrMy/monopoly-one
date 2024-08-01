package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class AccountBlockedError(
    override val code: Int = 412
) : WrongReturnable