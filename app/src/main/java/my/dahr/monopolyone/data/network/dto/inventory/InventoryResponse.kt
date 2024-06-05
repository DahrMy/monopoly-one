package my.dahr.monopolyone.data.network.dto.inventory


import com.google.gson.annotations.SerializedName

data class InventoryResponse(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: DataJson? = null
)