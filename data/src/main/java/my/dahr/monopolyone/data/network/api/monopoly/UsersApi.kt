package my.dahr.monopolyone.data.network.api.monopoly

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.user.remote.dto.request.UserParamsData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("users.get")
    fun getUsersList(
        @Body body: UserParamsData
    ): Call<BaseResponse>
}