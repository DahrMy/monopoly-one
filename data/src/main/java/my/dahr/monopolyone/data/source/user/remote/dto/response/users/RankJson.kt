package my.dahr.monopolyone.data.network.dto.response.users


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RankJson(
    @Expose
    @SerializedName("hidden")
    val hidden: Int? = null
)