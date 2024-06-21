package my.dahr.monopolyone.domain.models.inventory.protos



data class Drop(
    val isRare: Int,
    val isSecondary: Int,
    val itemProtoId: Int
)