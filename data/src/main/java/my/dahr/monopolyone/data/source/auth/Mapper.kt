package my.dahr.monopolyone.data.source.auth

import my.dahr.monopolyone.data.source.auth.local.DeserializedSession
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthRefreshRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthSignInRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthTotpVerifyRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse
import my.dahr.monopolyone.domain.currentTimeInSec
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.model.login.TotpInputData
import my.dahr.monopolyone.domain.model.login.TotpToken
import my.dahr.monopolyone.domain.model.session.AccessToken
import my.dahr.monopolyone.domain.model.session.RefreshToken
import my.dahr.monopolyone.domain.model.session.Session

fun SessionResponse.toSession() = Session(
    accessToken = AccessToken(data.accessToken),
    refreshToken = RefreshToken(data.refreshToken),
    userId = data.userId,
    expiresAt = data.expiresIn + currentTimeInSec,
    lifespan = data.expiresIn
)

fun DeserializedSession.toSession() = Session(
    accessToken = AccessToken(accessToken),
    refreshToken = RefreshToken(refreshToken),
    userId = userId,
    expiresAt = expiresAt,
    lifespan = lifespan,
)

fun Session.toDeserialized() = DeserializedSession(
    accessToken = accessToken.token,
    refreshToken = refreshToken.token,
    userId = userId,
    expiresAt = expiresAt,
    lifespan = lifespan,
)

fun TotpResponse.toTotpToken() = TotpToken(
    token = data.totpSessionToken
)

fun LoginInputData.toRequest() = AuthSignInRequest(
    email = email,
    password = password
)

fun TotpInputData.toRequest() = AuthTotpVerifyRequest(
    totpSessionToken = totpToken.token,
    code = code.toString()
)

fun RefreshToken.toRequest() = AuthRefreshRequest(
    refreshToken = token
)