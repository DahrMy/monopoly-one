package my.dahr.monopolyone.data.source.auth.local

data class DeserializedSession(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val lifespan: Long
)