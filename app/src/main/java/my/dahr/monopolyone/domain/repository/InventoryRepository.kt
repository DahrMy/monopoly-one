package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.models.inventory.Item

interface InventoryRepository {
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