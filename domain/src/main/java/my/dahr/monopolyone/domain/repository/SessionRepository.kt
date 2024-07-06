package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.LoginOutputData
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.model.login.TotpInputData

/**
 * The repository creates contract for session and login operations.
 */
interface SessionRepository {

    /**
     * Gets [LoginOutputData] from an API request using [LoginInputData].
     * @return [LoginOutputData] or [WrongReturnable] if something went wrong.
     */
    fun getLoginOutputData(loginInputData: LoginInputData): Returnable

    /**
     * Gets [Session] from an API request using [totpInputData].
     * @return [Session] or [WrongReturnable] if something went wrong.
     */
    fun getSession(totpInputData: TotpInputData): Returnable

    /**
     * Gets stored [Session] from local storage.
     */
    fun getStoredSession(): Session

    /**
     * Save [Session] into local storage.
     */
    fun saveSession(session: Session)

}