package my.dahr.monopolyone.domain.model.friends.params

import my.dahr.monopolyone.domain.model.session.AccessToken

data class RequestsParams(
    val accessToken: String,
    val type: String,
    val offset: Int,
    val count: Int,
)