package my.dahr.monopolyone.data.network.dto.friends.list


import com.google.gson.annotations.SerializedName

data class FriendsResponse(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: DataJson? = null
)