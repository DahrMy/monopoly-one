package my.dahr.monopolyone.data.source.auth

import my.dahr.monopolyone.data.source.auth.local.DeserializedSession
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthSignInRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthTotpVerifyRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse
import my.dahr.monopolyone.domain.currentTimeInSec
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.model.login.TotpInputData
import my.dahr.monopolyone.domain.model.login.TotpToken
import my.dahr.monopolyone.domain.model.session.Session

fun SessionResponse.toSession() = Session(
    accessToken = data.accessToken,
    refreshToken = data.refreshToken,
    userId = data.userId,
    expiresAt = data.expiresIn + currentTimeInSec,
    lifespan = data.expiresIn
)

fun DeserializedSession.toSession() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId,
    expiresAt = expiresAt,
    lifespan = lifespan,
)

fun Session.toParcelable() = DeserializedSession(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId,
    expiresAt = expiresAt,
    lifespan = lifespan,
)

fun TotpResponse.toTotpToken() = TotpToken(
    token = data.totpSessionToken
)

fun LoginInputData.toAuthSignInRequest() = AuthSignInRequest(
    email = email,
    password = password
)

fun TotpInputData.toAuthTotpVerifyRequest() = AuthTotpVerifyRequest(
    totpSessionToken = totpToken.token,
    code = code.toString()
)