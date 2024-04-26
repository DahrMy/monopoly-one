package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.FriendsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FriendsApi {
    @GET("friends.get")
    suspend fun getFriendsList(
        @Query("user_id") userId: Any,
        @Query("online") online: Boolean,
        @Query("add_user") addUser: Boolean,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
    ): FriendsResponse
}