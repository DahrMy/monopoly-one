package my.dahr.monopolyone.data.network.dto.response.friends.delete

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteParamsData(
    @Expose
    @SerializedName("access_token")
    val access_token: String,
    @Expose
    @SerializedName("user_id")
    val userId: Any
)
