package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {
    @POST("auth.signin")
    fun authSignInRequest(@Body body: Map<String, String>): Call<BaseResponse>

    @POST("auth.totpVerify")
    fun authTotpVerify(@Body body: Map<String, String>): Call<BaseResponse>

    @POST("auth.refresh")
    fun authRefreshRequest(@Body body: Pair<String, String>): Call<SessionResponse>

}