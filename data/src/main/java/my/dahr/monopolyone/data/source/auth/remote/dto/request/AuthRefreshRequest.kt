package my.dahr.monopolyone.data.source.auth.remote.dto.request

import com.google.gson.annotations.SerializedName

data class AuthRefreshRequest(
    @SerializedName("refresh_token")
    private val refreshToken: String
)