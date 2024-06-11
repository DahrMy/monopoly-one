package my.dahr.monopolyone.data.network.dto.response.friends.add


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

data class AddResponse(
    @Expose
    @SerializedName("code")
    override val code: Int,
    @Expose
    override val description: String? = null,
    @Expose
    override val data: Any? = null
): BaseResponse(code, description, data)