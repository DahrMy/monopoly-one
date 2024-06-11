package my.dahr.monopolyone.data.network.dto.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.dahr.monopolyone.data.models.RequestStatus.*
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.TotpResponse
import my.dahr.monopolyone.data.network.dto.response.error.DefaultErrorResponse
import my.dahr.monopolyone.data.network.dto.response.error.FrequentTotpErrorResponse
import my.dahr.monopolyone.data.network.dto.response.error.ParamInvalidErrorResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.domain.models.friends.requests.FriendsRequests
import java.lang.reflect.Type

class MonopolyDeserializer : JsonDeserializer<BaseResponse> {

    override fun deserialize(
        json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext
    ): BaseResponse {

        val code = json.asJsonObject.get("code").asInt
        val status = identifyByCode(code)

        return when (status) { // TODO: Handle more errors
            Success -> identifyByData(json, context)
            FrequentTotpError -> context.deserialize(json, FrequentTotpErrorResponse::class.java)
            ParamInvalidError -> context.deserialize(json, ParamInvalidErrorResponse::class.java)
            else -> context.deserialize(json, DefaultErrorResponse::class.java)
        }

    }

    private fun identifyByData(
        json: JsonElement, context: JsonDeserializationContext
    ): BaseResponse {
        val dataJson = json.asJsonObject.get("data")
        val hasTotpToken = dataJson.asJsonObject.has("totp_session_token")
        val isFriendsResponse = dataJson.asJsonObject.has("friends")
        val isFriendsRequestsResponse = dataJson.asJsonObject.has("requests")

        return when { // TODO: Add more responses
            hasTotpToken -> context.deserialize(json, TotpResponse::class.java)
            isFriendsResponse -> context.deserialize(json, FriendsResponse::class.java)
            isFriendsRequestsResponse -> context.deserialize(json, FriendsRequests::class.java)
            else -> context.deserialize(json, SessionResponse::class.java)
        }

    }

    private fun identifyByCode(code: Int) = RequestStatus.entries.find { status ->
        status.code == code
    } ?: UndefinedError

}