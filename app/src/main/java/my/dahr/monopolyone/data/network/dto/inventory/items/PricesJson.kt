package my.dahr.monopolyone.data.network.dto.inventory.items


import com.google.gson.annotations.SerializedName

data class PricesJson(
    @SerializedName("buy")
    val buy: Int? = null
)