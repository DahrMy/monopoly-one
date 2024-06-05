package my.dahr.monopolyone.data.network.dto.response.error


import com.google.gson.annotations.Expose
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

data class DefaultErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String
) : BaseResponse(code, description, null)