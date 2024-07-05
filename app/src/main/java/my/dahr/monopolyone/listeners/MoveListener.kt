package my.dahr.monopolyone.listeners

import my.dahr.monopolyone.domain.models.inventory.items.Item

interface MoveListener {
    fun onItemMoved(fromAdapter: Int, toAdapter: Int, item: Item, slot: Int)
}