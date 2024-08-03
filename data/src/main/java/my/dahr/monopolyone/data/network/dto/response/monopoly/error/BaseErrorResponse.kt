package my.dahr.monopolyone.data.network.dto.response.monopoly.error

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse

open class BaseErrorResponse(
    override val code: Int,
    override val description: String?,
    override val `data`: Any?
) : BaseResponse(code, description, data)
