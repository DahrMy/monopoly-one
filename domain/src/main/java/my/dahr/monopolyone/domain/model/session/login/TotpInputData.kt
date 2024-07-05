package my.dahr.monopolyone.domain.model.session.login

/**
 * A model that is using for [VerifyTotpUseCase]. //TODO: Fix warning
 */
data class TotpInputData(
    val totpToken: TotpToken,
    val code: Int
)