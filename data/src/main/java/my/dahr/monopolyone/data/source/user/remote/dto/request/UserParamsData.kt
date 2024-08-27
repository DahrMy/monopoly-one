package my.dahr.monopolyone.data.source.user.remote.dto.request

data class UserParamsData(
    private val userId: Any,
    private val userIds: Set<Int>,
    private val type: String
)