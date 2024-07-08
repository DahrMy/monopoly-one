package my.dahr.monopolyone.domain.usecase.session

import my.dahr.monopolyone.domain.currentTimeInSec
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.repository.NetworkRepository
import my.dahr.monopolyone.domain.repository.SessionRepository

class RequireSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val networkRepository: NetworkRepository
) {
    /**
     * Gives a session object from local storage.
     * @return [Session] or [WrongReturnable] if it isn't possible to obtain an actual session
     * or **null** if session doesn't exist.
     */
    suspend operator fun invoke(): Returnable? {
        val session = sessionRepository.getStoredSession() ?: return null
        val currentIp = networkRepository.getCurrentIp()

        val isExpired = (session.expiresAt - currentTimeInSec) <= 30 // thirty seconds for reserve

        return if (isExpired) {
            sessionRepository.refreshSession(session)
        } else if (networkRepository.getStoredIp() != currentIp) {
            networkRepository.saveIp(currentIp)
            /* return */ sessionRepository.refreshSession(session)
        } else {
            session
        }
    }
}