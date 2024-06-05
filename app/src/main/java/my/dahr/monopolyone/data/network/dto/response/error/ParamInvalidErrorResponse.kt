package my.dahr.monopolyone.data.network.dto.response.error

import com.google.gson.annotations.Expose
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

data class ParamInvalidErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String,
    @Expose
    override val `data`: Data
) : BaseResponse(code, description, data) {
    data class Data(
        @Expose
        val issues: List<Issue>
    ) {
        data class Issue(
            @Expose
            val key: String,
            @Expose
            val message: String
        )
    }
}