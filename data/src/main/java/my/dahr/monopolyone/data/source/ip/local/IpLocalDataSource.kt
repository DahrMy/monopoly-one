package my.dahr.monopolyone.data.source.ip.local

sealed interface IpLocalDataSource {
    var storedIp: ParcelableIp
}