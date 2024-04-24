package my.dahr.monopolyone.network.api

import my.dahr.monopolyone.network.dto.FriendsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MonopolyApi {
    @GET("friends.get")
    suspend fun getFriendsList(
        @Query("user_id") userId: Any = "dahr_my",
        @Query("online") online: Boolean = false,
        @Query("add_user") addUser: Boolean = false,
        @Query("type") type: String = "short",
        @Query("offset") offset: Int = 0,
        @Query("count") count: Int = 20
    ): Response<FriendsResponse>
}