package my.dahr.monopolyone.data.network.api.monopoly


import my.dahr.monopolyone.data.network.dto.response.friends.add.AddParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteParamsData
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.friends.remote.dto.request.list.ListParamsData
import my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests.RequestsParamsData
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
        @Body body: AddParamsData
    ): Call<BaseResponse>

    @POST("friends.delete")
    fun deleteFriend(
        @Body body: DeleteParamsData
    ): Call<BaseResponse>
}