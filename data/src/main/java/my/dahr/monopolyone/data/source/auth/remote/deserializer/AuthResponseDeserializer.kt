package my.dahr.monopolyone.data.source.auth.remote.deserializer

import com.google.gson.JsonElement
import my.dahr.monopolyone.data.network.MonopolyResponseDeserializer
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.DefaultErrorResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse

class AuthResponseDeserializer : MonopolyResponseDeserializer() {

    override fun identifyByContent(json: JsonElement): Class<out BaseResponse> {
        val jsonObject = json.asJsonObject
        return when {
            jsonObject.has("access_token") -> SessionResponse::class.java
            jsonObject.has("totp_session_token") -> TotpResponse::class.java
            else -> DefaultErrorResponse::class.java
        }
    }

}