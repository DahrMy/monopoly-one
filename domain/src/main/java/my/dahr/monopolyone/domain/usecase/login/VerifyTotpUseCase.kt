package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.model.login.TotpInputData
import my.dahr.monopolyone.domain.model.login.TotpToken

class VerifyTotpUseCase {
    /**
     * Completes sign in after [SignInUseCase] returns [TotpToken].
     * @param totpInputData an object contains TOTP code and TotpToken.
     * @return [Session] if operation went successfully,
     * or [WrongReturnable] if something went wrong.
     */
    operator fun invoke(totpInputData: TotpInputData): Returnable {
        return TODO("Not yet implemented")
    }
}