package my.dahr.monopolyone.domain.model.user.params


data class UserParams(
    val userId: Any,
    val userIds: Set<Int>,
    val type: String
)
