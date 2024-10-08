@file:Suppress("UnusedImport")

package my.dahr.monopolyone.domain.usecase.login

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.repository.SessionRepository

class RefreshSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    /**
     * Gives a new session instance using `accessToken` in [Session] in an exist session.
     * @return [Session] or [WrongReturnable] if it isn't possible to obtain an actual session
     * or `null` if session doesn't exist.
     */
    suspend operator fun invoke(): Returnable? {
        val session = sessionRepository.getStoredSession() ?: return null
        return sessionRepository.refreshSession(session)
    }
}