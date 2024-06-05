package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.network.api.InventoryApi
import my.dahr.monopolyone.domain.datasource.InventoryDataSource
import my.dahr.monopolyone.domain.models.inventory.Item
import javax.inject.Inject

class InventoryDataSourceImpl @Inject constructor(
    private val inventoryApi: InventoryApi
) : InventoryDataSource {
    override suspend fun getInventoryList(
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addUserInfo: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
        shrink: Boolean
    ): List<Item> {
        return inventoryApi.getInventoryList(
            userId,
            includeStock,
            order,
            count,
            addUser,
            addUserInfo,
            addEquipped,
            addLegacy,
            shrink
        ).toUi().data.items
    }

}