package my.dahr.monopolyone.data.source.inventory.remote

import my.dahr.monopolyone.data.network.api.monopoly.InventoryApi
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.inventory.remote.dto.request.ItemsParamsData
import retrofit2.Call

class InventoryDataSourceImpl(private val inventoryApi: InventoryApi) : InventoryDataSource {
    override fun getItemsList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
    ): Call<BaseResponse> =
        inventoryApi.getInventoryList(
            accessToken,
            userId,
            includeStock,
            order,
            count,
            addUser,
            addEquipped,
            addLegacy
        )
}