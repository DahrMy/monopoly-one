package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.LoginOutputData
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.login.LoginInputData


class SignInUseCase {
    /**
     * Sign in operation UseCase.
     * @param loginData an object contains an email and a password.
     * @return [LoginOutputData] if signing in is successful,
     * or [WrongReturnable] if something went wrong.
     */
    operator fun invoke(loginData: LoginInputData): Returnable {
        return TODO("Not yet implemented")
    }
}