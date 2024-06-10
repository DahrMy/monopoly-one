package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.response.MyIpResponse
import retrofit2.http.GET

interface IpApi {
    @GET("/")
    suspend fun getMyIp(): MyIpResponse
}