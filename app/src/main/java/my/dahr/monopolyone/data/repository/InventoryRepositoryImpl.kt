package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.domain.datasource.InventoryDataSource
import my.dahr.monopolyone.domain.repository.InventoryRepository
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDataSource: InventoryDataSource

) : InventoryRepository {
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
    ) =
        inventoryDataSource.getInventoryList(
            accessToken,
            userId,
            includeStock,
            order,
            count,
            addUser,
            addEquipped,
            addLegacy,
            callback
        )

    override fun getInventoryDataList(
        itemProtoIds: Set<Int>,
        addLegacy: Boolean,
        addMetadata: Boolean,
        callback: MonopolyCallback<BaseResponse>
    ) {
        inventoryDataSource.getInventoryDataList(
            itemProtoIds, addLegacy, addMetadata, callback
        )
    }
}