package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.SerializedName

data class ItemJson(
    @SerializedName("case_item_proto_ids")
    val caseItemProtoIds: List<Int?>? = null,
    @SerializedName("collection_id")
    val collectionId: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("drop")
    val drop: List<DropJson?>? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("item_id")
    val itemId: Int? = null,
    @SerializedName("item_proto_id")
    val itemProtoId: Int? = null,
    @SerializedName("prices")
    val prices: PricesJson? = null,
    @SerializedName("quality_id")
    val qualityId: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("ts_owned")
    val tsOwned: Int? = null,
    @SerializedName("type")
    val type: Int? = null
)