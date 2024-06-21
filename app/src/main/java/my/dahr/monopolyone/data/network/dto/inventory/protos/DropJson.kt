package my.dahr.monopolyone.data.network.dto.inventory.protos


import com.google.gson.annotations.SerializedName

data class DropJson(
    @SerializedName("is_rare")
    val isRare: Int? = null,
    @SerializedName("is_secondary")
    val isSecondary: Int? = null,
    @SerializedName("item_proto_id")
    val itemProtoId: Int? = null
)