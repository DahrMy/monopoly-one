package my.dahr.monopolyone.data.network.dto.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.TotpResponse
import java.lang.reflect.Type

class LoginDeserializer : JsonDeserializer<BaseResponse> {

    override fun deserialize(
        json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext
    ): BaseResponse {
        val dataJson = json.asJsonObject.get("data")
        val hasTotp = dataJson.asJsonObject.has("totp_session_token") // TODO: Fix NullPointerException here

        return if (hasTotp) {
            context.deserialize(json, TotpResponse::class.java)
        } else {
            context.deserialize(json, SessionResponse::class.java)
        }
    }

}