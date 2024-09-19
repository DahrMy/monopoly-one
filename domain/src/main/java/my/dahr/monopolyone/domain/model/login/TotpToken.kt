package my.dahr.monopolyone.domain.model.login

import my.dahr.monopolyone.domain.model.LoginOutputData


/**
 * A model that contains a token for auth completing.
 */
data class TotpToken(
    val token: String
) : LoginOutputData {
    override fun toString() = token
}
