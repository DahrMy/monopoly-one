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
     * @see <a href="https://docs.mnpl.one/api/http/auth.signin">auth.signin</a>
     */
    suspend fun getLoginOutputData(loginInputData: LoginInputData): Returnable

    /**
     * Gets [Session] from an API request using [totpInputData].
     * @return [Session] or [WrongReturnable] if something went wrong.
     * @see <a href="https://docs.mnpl.one/api/http/auth.totpVerify">auth.totpVerify</a>
     */
    suspend fun getSession(totpInputData: TotpInputData): Returnable

    /**
     * Gets stored [Session] from local storage.
     * @return [Session] or **null** if session doesn't exist/
     */
    fun getStoredSession(): Session?

    /**
     * Save [Session] into local storage.
     */
    fun saveSession(session: Session)

    /**
     * Extend the [session][Session] by returning new [session][Session]
     * @see <a href="https://docs.mnpl.one/api/http/auth.refresh">auth.refresh</a>
     */
    suspend fun refreshSession(session: Session): Returnable
}