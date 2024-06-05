package my.dahr.monopolyone.data.network.dto.response

open class BaseResponse(
    open val code: Int,
    open val description: String?,
    open val `data`: Any?
)
