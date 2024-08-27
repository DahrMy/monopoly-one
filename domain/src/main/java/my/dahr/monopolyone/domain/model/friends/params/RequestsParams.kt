package my.dahr.monopolyone.domain.model.friends.params

data class RequestsParams(
    val accessToken: String,
    val type: String,
    val offset: Int,
    val count: Int,
)