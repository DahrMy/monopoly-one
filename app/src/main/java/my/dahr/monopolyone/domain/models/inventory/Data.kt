package my.dahr.monopolyone.domain.models.inventory



data class Data(
    val collections: List<Collection>,
    val count: Int,
    val items: List<Item>,
    val user: User
)