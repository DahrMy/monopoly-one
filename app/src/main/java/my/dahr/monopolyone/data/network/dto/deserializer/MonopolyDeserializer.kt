package my.dahr.monopolyone.data.network.dto.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import my.dahr.monopolyone.data.models.RequestStatus.*
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.dto.inventory.items.InventoryResponse
import my.dahr.monopolyone.data.network.dto.inventory.protos.ProtosResponse
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.EmptyResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.TotpResponse
import my.dahr.monopolyone.data.network.dto.response.error.DefaultErrorResponse
import my.dahr.monopolyone.data.network.dto.response.error.FrequentTotpErrorResponse
import my.dahr.monopolyone.data.network.dto.response.error.ParamInvalidErrorResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddResponse
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.requests.FriendsRequestsResponse
import my.dahr.monopolyone.data.network.dto.response.users.UsersResponse
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
        var hasTotpToken = false
        //friends
        var isFriendsResponse = false
        var isFriendsRequestsResponse = false
        var isAddResponse = false
        var isDeleteResponse = false
        //inventory
        var isInventoryResponse = false
        //users
        var isUsersResponse = false
        var isInventoryProtosResponse = false

        if (dataJson == null) {
            return context.deserialize(json, EmptyResponse::class.java)
        }

        if (dataJson.isJsonObject) {
            hasTotpToken = dataJson.asJsonObject.has("totp_session_token")
            //friends
            isFriendsResponse = dataJson.asJsonObject.has("friends")
            isFriendsRequestsResponse = dataJson.asJsonObject.has("requests")
            isAddResponse = dataJson.asJsonObject.has("add")
            isDeleteResponse = dataJson.asJsonObject.has("delete")
            //inventory
            isInventoryResponse = dataJson.asJsonObject.has("items")
            isInventoryProtosResponse = dataJson.asJsonObject.has("item_protos")
        } else {
            //users
            isUsersResponse = dataJson.asJsonArray.get(0).asJsonObject.has("rank")
        }

        return when { // TODO: Add more responses
            hasTotpToken -> context.deserialize(json, TotpResponse::class.java)
            //friends
            isFriendsResponse -> context.deserialize(json, FriendsResponse::class.java)
            isFriendsRequestsResponse -> context.deserialize(
                json,
                FriendsRequestsResponse::class.java
            )

            isAddResponse -> context.deserialize(json, AddResponse::class.java)
            isDeleteResponse -> context.deserialize(json, DeleteResponse::class.java)
            //users
            isUsersResponse -> context.deserialize(json, UsersResponse::class.java)
            //inventory
            isInventoryResponse -> context.deserialize(json, InventoryResponse::class.java)
            isInventoryProtosResponse -> context.deserialize(json, ProtosResponse::class.java)
            else -> context.deserialize(json, SessionResponse::class.java)
        }
    }

    private fun identifyByCode(code: Int) = RequestStatus.entries.find { status ->
        status.code == code
    } ?: UndefinedError

}