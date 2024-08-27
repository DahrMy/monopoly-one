package my.dahr.monopolyone.domain.models.inventory.items

import my.dahr.monopolyone.domain.model.SuccessfulReturnable

data class Inventory(
    val code: Int,
    val data: Data
):SuccessfulReturnable