package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.SerializedName

data class RankJson(
    @SerializedName("hidden")
    val hidden: Int? = null
)