package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.LoginOutputData
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.repository.SessionRepository

/**
 * Sign in operation UseCase.
 * @return [LoginOutputData] if signing in is successful,
 * or [WrongReturnable] if something went wrong.
 */
class SignInUseCase(
    private val sessionRepository: SessionRepository
) {
    /**
     * @param loginData an object contains an email and a password
     */
    suspend operator fun invoke(loginData: LoginInputData) =
        sessionRepository.getLoginOutputData(loginData)
}