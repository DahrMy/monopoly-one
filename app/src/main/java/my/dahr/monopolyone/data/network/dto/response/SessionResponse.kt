package my.dahr.monopolyone.data.network.dto.response
import com.google.gson.annotations.SerializedName


data class SessionResponse(
    val code: Int,
    val data: Data
) {

    data class Data(
        @SerializedName("access_token")
        val accessToken: String,
        val expires: Long,
        @SerializedName("expires_in")
        val expiresIn: Long,
        @SerializedName("refresh_token")
        val refreshToken: String,
        @SerializedName("user_id")
        val userId: Int
    )

}

