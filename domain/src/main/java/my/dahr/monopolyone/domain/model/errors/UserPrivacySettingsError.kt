package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class UserPrivacySettingsError(
    override val code: Int = 701
) : WrongReturnable