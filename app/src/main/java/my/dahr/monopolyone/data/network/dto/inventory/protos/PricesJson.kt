package my.dahr.monopolyone.data.network.dto.inventory.protos


import com.google.gson.annotations.SerializedName

data class PricesJson(
    @SerializedName("buy")
    val buy: Int? = null,
    @SerializedName("quick_sell")
    val quickSell: Double? = null
)