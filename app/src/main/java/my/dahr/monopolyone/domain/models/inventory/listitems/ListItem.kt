package my.dahr.monopolyone.domain.models.inventory.listitems

import my.dahr.monopolyone.domain.models.inventory.items.Item

sealed class ListItem {
    data class NumberItem(val number: Int) : ListItem()
    data class InventoryItem(val item: Item) : ListItem()
}