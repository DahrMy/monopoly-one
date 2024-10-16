package my.dahr.monopolyone.data.source.auth.remote.dto.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthTotpVerifyRequest(
    @SerializedName("totp_session_token")
    @Expose
    private val totpSessionToken: String,
    @Expose
    private val code: String
)
