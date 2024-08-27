package my.dahr.monopolyone.data.source.inventory.remote

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.inventory.remote.dto.request.ItemsParamsData
import retrofit2.Call

interface InventoryDataSource {
    fun getItemsList(itemsParamsData: ItemsParamsData) : Call<BaseResponse>
}