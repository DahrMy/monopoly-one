package my.dahr.monopolyone.data.network.dto.response.monopoly.error


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FrequentTotpErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String,
    @Expose
    override val `data`: Data
) : BaseErrorResponse(code, description, `data`) {
    data class Data(
        @SerializedName("retry_after")
        @Expose
        val retryAfter: Long
    )
}