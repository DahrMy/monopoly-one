package my.dahr.monopolyone.data.network.dto.friends.list


import com.google.gson.annotations.SerializedName

data class RankJson(
    @SerializedName("hidden")
    val hidden: Int? = null
)