package my.dahr.monopolyone.data.network.dto.response.friends.requests


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RankRequestsJson(
    @Expose
    @SerializedName("hidden")
    val hidden: Int? = null
)