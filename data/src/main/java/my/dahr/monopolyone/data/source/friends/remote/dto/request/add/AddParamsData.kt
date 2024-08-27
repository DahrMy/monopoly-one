package my.dahr.monopolyone.data.network.dto.response.friends.add

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddParamsData(
    @Expose
    @SerializedName("access_token")
    val access_token: String,
    @Expose
    @SerializedName("user_id")
    val userId: Any
)
