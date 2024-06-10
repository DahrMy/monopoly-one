package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.domain.models.inventory.Item

interface InventoryDataSource {
    suspend fun getInventoryList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
    ):List<Item>
}