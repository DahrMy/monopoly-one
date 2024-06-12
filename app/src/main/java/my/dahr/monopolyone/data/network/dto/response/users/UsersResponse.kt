package my.dahr.monopolyone.data.network.dto.response.users


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

data class UsersResponse(
    @Expose
    @SerializedName("code")
    override val code: Int,
    @Expose
    override val description: String? = null,
    @Expose
    @SerializedName("data")
    override val data: List<DataJson?>? = null
):BaseResponse(code, description, data)