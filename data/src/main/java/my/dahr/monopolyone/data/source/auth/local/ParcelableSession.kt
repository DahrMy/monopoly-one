package my.dahr.monopolyone.data.source.auth.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableSession(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val lifespan: Long
) : Parcelable