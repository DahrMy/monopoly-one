package my.dahr.monopolyone.data.source.auth.remote.dto.request

import com.google.gson.annotations.SerializedName

data class AuthTotpVerifyRequest(
    @SerializedName("totp_session_token")
    private val totpSessionToken: String,
    private val code: String
)
