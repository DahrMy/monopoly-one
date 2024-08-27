package my.dahr.monopolyone.data.source.inventory.remote

import my.dahr.monopolyone.data.network.api.monopoly.InventoryApi
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.inventory.remote.dto.request.ItemsParamsData
import retrofit2.Call

class InventoryDataSourceImpl(private val inventoryApi: InventoryApi) : InventoryDataSource {
    override fun getItemsList(itemsParamsData: ItemsParamsData): Call<BaseResponse> =
        inventoryApi.getInventoryList(itemsParamsData)
}