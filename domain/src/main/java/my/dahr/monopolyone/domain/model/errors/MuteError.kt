package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class MuteError(override val code: Int = 104) : WrongReturnable