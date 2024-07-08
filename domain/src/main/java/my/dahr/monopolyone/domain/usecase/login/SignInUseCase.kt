package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.LoginOutputData
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.repository.SessionRepository


class SignInUseCase(
    private val sessionRepository: SessionRepository
) {
    /**
     * Sign in operation UseCase.
     * @param loginData an object contains an email and a password.
     * @return [LoginOutputData] if signing in is successful,
     * or [WrongReturnable] if something went wrong.
     */
    suspend operator fun invoke(loginData: LoginInputData) =
        sessionRepository.getLoginOutputData(loginData)
}