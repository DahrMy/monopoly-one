package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.inventory.params.ItemParams

interface InventoryRepository {
    suspend fun getItemsList(
        itemParams: ItemParams
    ): Returnable
}