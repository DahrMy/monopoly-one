package my.dahr.monopolyone.data.network.dto.response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SessionResponse(

    @Expose
    override val code: Int,

    @Expose
    override val description: String?,

    @Expose
    override val data: Data

) : BaseResponse(code, description, data) {

    data class Data(

        @Expose
        @SerializedName("access_token")
        val accessToken: String,

        @Expose
        val expires: Long,

        @Expose
        @SerializedName("expires_in")
        val expiresIn: Long,

        @Expose
        @SerializedName("refresh_token")
        val refreshToken: String,

        @Expose
        @SerializedName("user_id")
        val userId: Int

    )

}

