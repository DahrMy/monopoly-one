package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class AccessDenyError(
    override val code: Int = 4
) : WrongReturnable
