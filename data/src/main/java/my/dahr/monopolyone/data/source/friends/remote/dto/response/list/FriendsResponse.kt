package my.dahr.monopolyone.data.network.dto.response.friends.list


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse

data class FriendsResponse(
    @Expose
    @SerializedName("code")
    override val code: Int,
    @Expose
    override val description: String? = null,
    @Expose
    @SerializedName("data")
    override val data: DataJson? = null
) : BaseResponse(code, description, data)