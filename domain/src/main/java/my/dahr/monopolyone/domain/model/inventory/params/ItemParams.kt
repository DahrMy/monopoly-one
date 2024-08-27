package my.dahr.monopolyone.domain.model.inventory.params

import my.dahr.monopolyone.domain.model.ItemsParamsData

data class ItemParams(
    val accessToken: String,
    val userId: Any,
    val includeStock: Boolean,
    val order: String,
    val count: Int,
    val addUser: Boolean,
    val addEquipped: String,
    val addLegacy: Boolean
)