package my.dahr.monopolyone.data.network.dto.friends.add


import com.google.gson.annotations.SerializedName

data class AddResponseJson(
    @SerializedName("access_token")
    val access_token: String,
    @SerializedName("user_id")
    val userId: Any
)