package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.friends.add.AddResponseJson
import my.dahr.monopolyone.data.network.dto.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.friends.requests.FriendsRequestsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("friends.getRequests")
    suspend fun getFriendsRequestsList(
        @Query("access_token") accessToken: String,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
    ): FriendsRequestsResponse

    @POST("friends.add")
    suspend fun addFriend(
        @Body body: AddResponseJson
    )

}