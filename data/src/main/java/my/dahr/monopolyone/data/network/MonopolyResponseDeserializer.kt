package my.dahr.monopolyone.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.BaseErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.DefaultErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.FrequentTotpErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.ParamInvalidErrorResponse
import java.lang.reflect.Type

abstract class MonopolyResponseDeserializer : JsonDeserializer<BaseResponse> {

    override fun deserialize(
        json: JsonElement, typeOfT: Type, context: JsonDeserializationContext
    ): BaseResponse {
        val code = json.asJsonObject.get("code").asInt

        val clazz: Class<out BaseResponse> = if (code == 0) {
            identifyByCode(code)
        } else {
            identifyByContent(json)
        }

        return context.deserialize(json, clazz)
    }

    internal abstract fun identifyByContent(json: JsonElement): Class<out BaseResponse>

    private fun identifyByCode(code: Int): Class<out BaseErrorResponse> = when (code) {
        2 -> ParamInvalidErrorResponse::class.java
        414 -> FrequentTotpErrorResponse::class.java
        else -> DefaultErrorResponse::class.java
    }

}