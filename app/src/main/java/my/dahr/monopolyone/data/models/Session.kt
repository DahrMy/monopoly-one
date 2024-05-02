package my.dahr.monopolyone.data.models

data class Session (
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long
)
