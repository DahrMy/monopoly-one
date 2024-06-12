package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataJson(
    @Expose
    @SerializedName("collections")
    val collections: List<CollectionJson?>? = null,
    @Expose
    @SerializedName("count")
    val count: Int? = null,
    @Expose
    @SerializedName("items")
    val items: List<ItemJson?>? = null,
    @Expose
    @SerializedName("user")
    val user: UserJson? = null
)