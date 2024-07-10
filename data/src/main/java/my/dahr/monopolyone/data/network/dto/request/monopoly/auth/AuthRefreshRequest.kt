package my.dahr.monopolyone.data.network.dto.request.monopoly.auth

import com.google.gson.annotations.SerializedName

data class AuthRefreshRequest(
    @SerializedName("refresh_token")
    private val refreshToken: String
)