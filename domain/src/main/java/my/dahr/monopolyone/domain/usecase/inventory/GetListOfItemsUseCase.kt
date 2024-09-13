package my.dahr.monopolyone.domain.usecase.inventory

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.inventory.params.ItemParams
import my.dahr.monopolyone.domain.repository.InventoryRepository

class GetListOfItemsUseCase(private val repository: InventoryRepository) {
    suspend operator fun invoke(itemParams: ItemParams): Returnable{
        return repository.getItemsList(itemParams)
    }
}