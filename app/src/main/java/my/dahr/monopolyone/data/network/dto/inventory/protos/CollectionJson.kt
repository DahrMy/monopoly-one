package my.dahr.monopolyone.data.network.dto.inventory.protos


import com.google.gson.annotations.SerializedName

data class CollectionJson(
    @SerializedName("collection_id")
    val collectionId: Int? = null,
    @SerializedName("title")
    val title: String? = null
)