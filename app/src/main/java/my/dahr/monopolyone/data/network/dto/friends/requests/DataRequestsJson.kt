package my.dahr.monopolyone.data.network.dto.friends.requests


import com.google.gson.annotations.SerializedName

data class DataRequestsJson(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("requests")
    val requests: List<RequestJson?>? = null
)