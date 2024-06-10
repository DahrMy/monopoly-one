package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.models.inventory.Item

interface InventoryRepository {
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