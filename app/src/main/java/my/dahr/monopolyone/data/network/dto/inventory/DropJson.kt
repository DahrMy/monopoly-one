package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.SerializedName

data class DropJson(
    @SerializedName("item_proto_id")
    val itemProtoId: Int? = null
)