package my.dahr.monopolyone.data.network.dto.response.friends.list


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataJson(
    @Expose
    @SerializedName("count")
    val count: Int? = null,
    @Expose
    @SerializedName("friends")
    val friends: List<FriendJson?>? = null
)