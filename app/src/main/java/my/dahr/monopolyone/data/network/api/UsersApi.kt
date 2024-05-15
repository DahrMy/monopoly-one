package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.users.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("users.get")
    suspend fun getUsersList(
        @Query("user_id") userId: Any,
        @Query("user_ids") userIds: Set<Int>,
        @Query("type") type: String
        ): UsersResponse
}