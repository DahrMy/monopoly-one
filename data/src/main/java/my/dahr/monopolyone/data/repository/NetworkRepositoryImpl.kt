package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.source.ip.local.IpLocalDataSource
import my.dahr.monopolyone.data.source.ip.remote.IpRemoteDataSource
import my.dahr.monopolyone.data.source.ip.remote.MyIpResponse
import my.dahr.monopolyone.data.source.ip.toIp
import my.dahr.monopolyone.data.source.ip.toDeserializedIp
import my.dahr.monopolyone.domain.model.session.Ip
import my.dahr.monopolyone.domain.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NetworkRepositoryImpl(
    private val ipRemoteDataSource: IpRemoteDataSource,
    private val ipLocalDataSource: IpLocalDataSource
) : NetworkRepository {

    override suspend fun getCurrentIp(): Ip? = suspendCoroutine { continuation ->
        ipRemoteDataSource.getMyIp().enqueue(object : Callback<MyIpResponse> {

            override fun onResponse(call: Call<MyIpResponse>, response: Response<MyIpResponse>) {
                if (response.isSuccessful) {
                    val ip = response.body()?.toIp()
                    continuation.resume(ip)
                } else {
                    continuation.resume(null)
                }
            }

            override fun onFailure(call: Call<MyIpResponse>, t: Throwable) {
                continuation.resume(null)
            }

        })
    }

    override fun getStoredIp(): Ip? = ipLocalDataSource.storedIp?.toIp()

    override fun saveIp(ip: Ip) {
        ipLocalDataSource.storedIp = ip.toDeserializedIp()
    }
}