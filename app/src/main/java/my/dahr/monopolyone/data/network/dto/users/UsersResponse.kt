package my.dahr.monopolyone.data.network.dto.users


import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val `data`: List<DataJson?>? = null
)