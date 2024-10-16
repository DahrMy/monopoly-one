package my.dahr.monopolyone.data.source.inventory.remote.deserializer

import com.google.gson.JsonElement
import my.dahr.monopolyone.data.network.MonopolyResponseDeserializer
import my.dahr.monopolyone.data.network.dto.inventory.items.InventoryResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.DefaultErrorResponse

class InventoryResponseDeserializer : MonopolyResponseDeserializer() {

    override fun identifyByContent(json: JsonElement): Class<out BaseResponse> {
        val jsonObject = json.asJsonObject
        return when {
            jsonObject.has("items") -> InventoryResponse::class.java
            else -> DefaultErrorResponse::class.java
        }
    }
}