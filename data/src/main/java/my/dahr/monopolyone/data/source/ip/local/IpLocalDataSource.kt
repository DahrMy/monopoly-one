package my.dahr.monopolyone.data.source.ip.local

sealed interface IpLocalDataSource {
    fun getStoredIp(): ParcelableIp
    fun saveIp(response: ParcelableIp)
}