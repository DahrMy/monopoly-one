package my.dahr.monopolyone.data.network.dto.response.friends.requests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataRequestsJson(
    @Expose
    @SerializedName("count")
    val count: Int? = null,
    @Expose
    @SerializedName("requests")
    val requests: List<RequestJson?>? = null
)