package my.dahr.monopolyone.data.network.dto.response.friends.delete

import com.google.gson.annotations.SerializedName

data class DeleteResponseJson(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("user_id")
    val userId: Any
)
