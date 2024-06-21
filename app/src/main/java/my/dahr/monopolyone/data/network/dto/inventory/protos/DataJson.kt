package my.dahr.monopolyone.data.network.dto.inventory.protos


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataJson(
    @Expose
    @SerializedName("collections")
    val collections: List<CollectionJson?>? = null,
    @Expose
    @SerializedName("item_protos")
    val itemProtos: List<ItemProtoJson?>? = null
)