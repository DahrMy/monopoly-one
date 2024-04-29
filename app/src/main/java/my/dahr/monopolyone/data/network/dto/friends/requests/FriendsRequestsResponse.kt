package my.dahr.monopolyone.data.network.dto.friends.requests


import com.google.gson.annotations.SerializedName

data class FriendsRequestsResponse(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: DataRequestsJson? = null
)