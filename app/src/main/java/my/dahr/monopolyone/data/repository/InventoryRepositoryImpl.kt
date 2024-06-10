package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.domain.datasource.InventoryDataSource
import my.dahr.monopolyone.domain.models.inventory.Item
import my.dahr.monopolyone.domain.repository.InventoryRepository
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDataSource: InventoryDataSource

) : InventoryRepository {
    override suspend fun getInventoryList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
    ): List<Item> =
        inventoryDataSource.getInventoryList(
            accessToken,
            userId,
            includeStock,
            order,
            count,
            addUser,
            addEquipped,
            addLegacy,
        )
}