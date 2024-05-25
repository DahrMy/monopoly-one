package my.dahr.monopolyone.data.network.dto.response.friends.list


import com.google.gson.annotations.SerializedName

data class DataJson(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("friends")
    val friends: List<FriendJson?>? = null
)