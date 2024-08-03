package my.dahr.monopolyone.data.network.dto.response.monopoly.error


import com.google.gson.annotations.Expose

data class DefaultErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String
) : BaseErrorResponse(code, description, null)