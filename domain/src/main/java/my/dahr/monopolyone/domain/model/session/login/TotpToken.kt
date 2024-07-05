package my.dahr.monopolyone.domain.model.session.login

import my.dahr.monopolyone.domain.model.session.LoginOutputData

/**
 * A model that contains a token for auth completing.
 */
data class TotpToken(
    val token: String
) : LoginOutputData
