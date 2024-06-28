package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

interface InventoryRepository {

    fun getInventoryList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
        callback: MonopolyCallback<BaseResponse>
    )

    fun getInventoryDataList(
        itemProtoIds: Set<Int>,
        addLegacy: Boolean,
        addMetadata: Boolean,
        callback: MonopolyCallback<BaseResponse>
    )
}