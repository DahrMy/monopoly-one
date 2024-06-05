package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.SerializedName

data class DataJson(
    @SerializedName("collections")
    val collections: List<CollectionJson?>? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("items")
    val items: List<ItemJson?>? = null,
    @SerializedName("user")
    val user: UserJson? = null
)