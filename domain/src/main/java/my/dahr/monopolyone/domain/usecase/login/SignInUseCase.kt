package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.model.session.login.*

class SignInUseCase {
    /**
     * TODO: Not yet implemented
     * @return [Session] or [TotpToken] if signing in is successful,
     * or [WrongReturnable] if something went wrong.
     */
    operator fun invoke(loginData: LoginInputData): Returnable {
        return TODO("Not yet implemented")
    }
}