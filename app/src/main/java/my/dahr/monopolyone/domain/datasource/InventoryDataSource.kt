package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.domain.models.inventory.Item

interface InventoryDataSource {
    suspend fun getInventoryList(
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addUserInfo: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
        shrink: Boolean
    ):List<Item>
}