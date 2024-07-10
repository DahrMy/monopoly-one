package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.network.dto.response.MyIpResponse
import retrofit2.http.GET

interface IpApi {

    /**
     * @see <a href="https://www.myip.com/api-docs/">myip.com/api-docs</a>
     */
    @GET("/")
    suspend fun getMyIp(): MyIpResponse
}