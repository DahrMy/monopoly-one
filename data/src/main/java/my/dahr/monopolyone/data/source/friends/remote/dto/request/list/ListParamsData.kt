package my.dahr.monopolyone.data.source.friends.remote.dto.request.list

data class ListParamsData(
    private val userId: Any,
    private val online: Boolean,
    private val addUser: Boolean,
    private val type: String,
    private val offset: Int,
    private val count: Int
)
