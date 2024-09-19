package my.dahr.monopolyone.data.network.dto.response.monopoly.error

import com.google.gson.annotations.Expose

internal data class ParamInvalidErrorResponse(
    @Expose
    override val code: Int,
    @Expose
    override val description: String,
    @Expose
    override val `data`: Data
) : BaseErrorResponse(code, description, data) {
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