package my.dahr.monopolyone.data.network.dto.request.monopoly.auth

import com.google.gson.annotations.SerializedName

data class AuthTotpVerifyRequest(
    @SerializedName("totp_session_token")
    private val totpSessionToken: String,
    private val code: String
)
