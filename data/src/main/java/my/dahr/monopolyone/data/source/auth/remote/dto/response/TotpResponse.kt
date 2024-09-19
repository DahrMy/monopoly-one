package my.dahr.monopolyone.data.source.auth.remote.dto.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse

data class TotpResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String?,
    @Expose
    override val `data`: Data
) : BaseResponse(code, description, `data`) {
    data class Data(
        @Expose
        @SerializedName("totp_session_token")
        val totpSessionToken: String
    )
}