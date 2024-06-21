package my.dahr.monopolyone.data.network.dto.inventory.items


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemJson(
    @Expose
    @SerializedName("case_item_proto_ids")
    val caseItemProtoIds: List<Int?>? = null,
    @Expose
    @SerializedName("collection_id")
    val collectionId: Int? = null,
    @Expose
    @SerializedName("description")
    val description: String? = null,
    @Expose
    @SerializedName("drop")
    val drop: List<DropJson?>? = null,
    @Expose
    @SerializedName("image")
    val image: String? = null,
    @Expose
    @SerializedName("item_id")
    val itemId: Int? = null,
    @Expose
    @SerializedName("item_proto_id")
    val itemProtoId: Int? = null,
    @Expose
    @SerializedName("prices")
    val prices: PricesJson? = null,
    @Expose
    @SerializedName("quality_id")
    val qualityId: Int? = null,
    @Expose
    @SerializedName("title")
    val title: String? = null,
    @Expose
    @SerializedName("ts_owned")
    val tsOwned: Int? = null,
    @Expose
    @SerializedName("type")
    val type: Int? = null
)