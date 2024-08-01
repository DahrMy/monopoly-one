package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class UserNotExistError(override val code: Int = 101) : WrongReturnable