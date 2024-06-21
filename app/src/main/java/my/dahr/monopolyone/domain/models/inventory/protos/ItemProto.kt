package my.dahr.monopolyone.domain.models.inventory.protos



data class ItemProto(
    val caseItemProtoIds: List<Int?>,
    val collectionId: Int,
    val description: String,
    val drop: List<Drop>,
    val image: String,
    val itemProtoId: Int,
    val keyItemProtoId: Int,
    val monopolyId: Int,
    val prices: Prices,
    val qualityId: Int,
    val title: String,
    val type: Int
)