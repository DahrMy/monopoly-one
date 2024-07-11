package my.dahr.monopolyone.data.network.api

import my.dahr.monopolyone.data.source.ip.remote.MyIpResponse
import retrofit2.Call
import retrofit2.http.GET

interface IpApi {

    /**
     * @see <a href="https://www.myip.com/api-docs/">myip.com/api-docs</a>
     */
    @GET("/")
    fun getMyIp(): Call<MyIpResponse>
}