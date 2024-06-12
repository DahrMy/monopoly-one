package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FriendsApi {
    @GET("friends.get")
    fun getFriendsList(
        @Query("user_id") userId: Any,
        @Query("online") online: Boolean,
        @Query("add_user") addUser: Boolean,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
    ): Call<BaseResponse>

    @GET("friends.getRequests")
    fun getFriendsRequestsList(
        @Query("access_token") accessToken: String,
        @Query("type") type: String,
        @Query("offset") offset: Int,
        @Query("count") count: Int,
    ): Call<BaseResponse>

    @POST("friends.add")
    fun addFriend(
        @Body body:  AddRequest
    ): Call<BaseResponse>

    @POST("friends.delete")
    fun deleteFriend(
        @Body body: DeleteRequest
    ): Call<BaseResponse>

}