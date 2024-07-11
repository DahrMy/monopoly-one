package my.dahr.monopolyone.data.source.ip.remote

import retrofit2.Call

sealed interface IpRemoteDataSource {
    fun getMyIp(): Call<MyIpResponse>
}