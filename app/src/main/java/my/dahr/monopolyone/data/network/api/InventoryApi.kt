package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.inventory.InventoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface InventoryApi {
    @GET("inventory.get")
    suspend fun getInventoryList(
        @Query("user_id") userId: Any,
        @Query("include_stock") includeStock: Boolean,
        @Query("order") order: String,
        @Query("count") count: Int,
        @Query("add_user") addUser: Boolean,
        @Query("add_user_info") addUserInfo: Boolean,
        @Query("add_equipped") addEquipped: String,
        @Query("add_legacy") addLegacy: Boolean,
        @Query("shrink") shrink: Boolean,
    ): InventoryResponse

}