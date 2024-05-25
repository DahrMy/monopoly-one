package my.dahr.monopolyone.data.network.dto.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TotpResponse(
    @Expose
    override val code: Int,
    @Expose
    override val data: Data
) : LoginBaseResponse(code, data) {
    data class Data(
        @Expose
        @SerializedName("totp_session_token")
        val totpSessionToken: String
    )
}