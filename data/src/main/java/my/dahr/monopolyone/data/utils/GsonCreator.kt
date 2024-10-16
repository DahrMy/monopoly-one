package my.dahr.monopolyone.data.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import my.dahr.monopolyone.data.network.MonopolyResponseDeserializer
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.DefaultErrorResponse

// TODO: Add KDoc
fun buildMonopolyGson(
    baseDtoClazz: Class<out BaseResponse>,
    deserializer: MonopolyResponseDeserializer
) = GsonBuilder()
    .registerTypeAdapter(baseDtoClazz, deserializer)
    .excludeFieldsWithoutExposeAnnotation()
    .create()

/**
 * This fun creates an instance of simple implementation of [MonopolyResponseDeserializer]
 * where fun `identifyByContent` always returns `DefaultErrorResponse::class.java`
 * @return [MonopolyResponseDeserializer]
 */
fun createPlainMonopolyDeserializerInstance() = object : MonopolyResponseDeserializer() {
    override fun identifyByContent(json: JsonElement) = DefaultErrorResponse::class.java
}