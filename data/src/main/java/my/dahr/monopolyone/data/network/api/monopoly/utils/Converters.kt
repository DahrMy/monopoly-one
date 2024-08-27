package my.dahr.monopolyone.data.network.api.monopoly.utils

import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.dto.response.SessionResponse

fun SessionResponse.Data.toSession() = Session(
    userId = userId,
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresAt = expiresIn + currentTimeInSec
)