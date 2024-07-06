package my.dahr.monopolyone.domain.model.login

import my.dahr.monopolyone.domain.usecase.login.VerifyTotpUseCase


/**
 * A model that is using for [VerifyTotpUseCase].
 */
data class TotpInputData(
    val totpToken: TotpToken,
    val code: Int
)