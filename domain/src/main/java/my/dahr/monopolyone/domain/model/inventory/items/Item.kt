package my.dahr.monopolyone.domain.models.inventory.items



data class Item(
    val caseItemProtoIds: List<Int?>,
    val collectionId: Int,
    val description: String,
    val drop: List<Drop>,
    val image: String,
    val itemId: Int,
    val itemProtoId: Int,
    val prices: Prices,
    val qualityId: Int,
    val title: String,
    val tsOwned: Int,
    val type: Int
)