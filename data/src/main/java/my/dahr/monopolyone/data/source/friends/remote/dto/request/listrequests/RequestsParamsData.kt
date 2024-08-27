package my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests

data class RequestsParamsData(
    private val accessToken: String,
    private val type: String,
    private val offset: Int,
    private val count: Int
)
