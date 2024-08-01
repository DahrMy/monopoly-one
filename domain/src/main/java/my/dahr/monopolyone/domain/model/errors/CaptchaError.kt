package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class CaptchaError(
    override val code: Int = 8
) : WrongReturnable