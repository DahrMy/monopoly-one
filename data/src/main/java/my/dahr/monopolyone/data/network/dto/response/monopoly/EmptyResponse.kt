package my.dahr.monopolyone.data.network.dto.response.monopoly

import com.google.gson.annotations.Expose

internal data class EmptyResponse(
    @Expose
    override val code: Int
) : BaseResponse(code, null, null)
