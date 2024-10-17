package my.dahr.monopolyone.domain.model

import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase

/**
 * A model contains an IP address. Using for [RequireSessionUseCase].
 */
data class Ip(
    val address: String
)