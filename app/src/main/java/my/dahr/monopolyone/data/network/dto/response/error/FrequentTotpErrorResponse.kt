package my.dahr.monopolyone.data.network.dto.response.error


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

data class FrequentTotpErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String,
    @Expose
    override val `data`: Data
) : BaseResponse(code, description, `data`) {
    data class Data(
        @SerializedName("retry_after")
        @Expose
        val retryAfter: Int
    )
}