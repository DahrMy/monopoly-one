package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.api.InventoryApi
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.domain.datasource.InventoryDataSource
import javax.inject.Inject

class InventoryDataSourceImpl @Inject constructor(
    private val inventoryApi: InventoryApi
) : InventoryDataSource {

    override fun getInventoryList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
        callback: MonopolyCallback<BaseResponse>
    ) {
        inventoryApi.getInventoryList(
            accessToken,
            userId,
            includeStock,
            order,
            count,
            addUser,
            addEquipped,
            addLegacy,
        ).enqueue(callback)
    }

    override fun getInventoryDataList(
        itemProtoIds: Set<Int>,
        addLegacy: Boolean,
        addMetadata: Boolean,
        callback: MonopolyCallback<BaseResponse>
    ) {
        inventoryApi.getInventoryDataList(
            itemProtoIds, addLegacy, addMetadata
        ).enqueue(callback)
    }
}