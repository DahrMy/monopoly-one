package my.dahr.monopolyone.data.source.ip.remote

import my.dahr.monopolyone.data.network.api.IpApi
import retrofit2.Call

class IpRetrofitDataSourceImpl(
    private val api: IpApi
) : IpRemoteDataSource {

    override fun getMyIp(): Call<MyIpResponse> = api.getMyIp()

}