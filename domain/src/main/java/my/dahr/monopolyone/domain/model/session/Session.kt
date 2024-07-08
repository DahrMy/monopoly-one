package my.dahr.monopolyone.domain.model.session

import my.dahr.monopolyone.domain.model.LoginOutputData

/**
 * A model that is using for a require-authentication API requests.
 * @see <a href="https://docs.mnpl.one/objects/Session">Session</a>
 * @param userId an id of a user that uses the session.
 * @param accessToken using in a require-authentication API requests.
 * @param refreshToken using in [auth.refresh](https://docs.mnpl.one/api/http/auth.refresh) API request.
 * @param expiresAt the exact time in Unix Time Stamp format indicating the expiring of the session.
 * @param lifespan delta time in seconds specify lifespan of the session.
 **/
data class Session (
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val lifespan: Long
) : LoginOutputData
