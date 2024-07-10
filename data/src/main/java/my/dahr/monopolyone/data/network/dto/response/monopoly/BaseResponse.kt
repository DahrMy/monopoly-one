package my.dahr.monopolyone.data.network.dto.response.monopoly

open class BaseResponse(
    open val code: Int,
    open val description: String?,
    open val `data`: Any?
) {
    override fun toString(): String {
        return "BaseResponse(code=$code, description=$description, `data`=${`data`.toString()})"
    }
}
