package my.dahr.monopolyone.data.network.api.monopoly

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.inventory.remote.dto.request.ItemsParamsData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface InventoryApi {
    @GET("inventory.get")
    fun getInventoryList(
        @Body body: ItemsParamsData
    ): Call<BaseResponse>

    @GET("data.getItemProtos")
    fun getInventoryDataList(
        @Query("item_proto_ids") itemProtoIds: Set<Int>,
        @Query("add_legacy") addLegacy: Boolean,
        @Query("add_metadata") addMetadata: Boolean
    ): Call<BaseResponse>

}