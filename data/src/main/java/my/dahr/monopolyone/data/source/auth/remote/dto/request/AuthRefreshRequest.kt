package my.dahr.monopolyone.data.source.auth.remote.dto.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthRefreshRequest(
    @SerializedName("refresh_token")
    @Expose
    private val refreshToken: String
)