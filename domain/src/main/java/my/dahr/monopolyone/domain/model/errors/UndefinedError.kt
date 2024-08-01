package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class UndefinedError(override val code: Int = 99) : WrongReturnable