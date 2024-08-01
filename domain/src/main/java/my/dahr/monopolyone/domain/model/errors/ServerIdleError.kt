package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class ServerIdleError(override val code: Int = 10) : WrongReturnable