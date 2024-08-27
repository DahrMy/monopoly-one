package my.dahr.monopolyone.domain.models.inventory.items



data class Data(
    val collections: List<Collection>,
    val count: Int,
    val items: List<Item>,
    val user: User
)