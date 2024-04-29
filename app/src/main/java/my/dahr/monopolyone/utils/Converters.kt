package my.dahr.monopolyone.utils

import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.dto.response.SessionResponse

fun SessionResponse.Data.toSession() = Session(
    userId = userId,
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresAt = expires + currentTimeInSec
)