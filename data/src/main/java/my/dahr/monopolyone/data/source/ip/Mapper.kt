package my.dahr.monopolyone.data.source.ip

import my.dahr.monopolyone.data.source.ip.local.DeserializedIp
import my.dahr.monopolyone.data.source.ip.remote.MyIpResponse
import my.dahr.monopolyone.domain.model.session.Ip

fun DeserializedIp.toIp() = Ip(
    address = address
)

fun MyIpResponse.toIp() = Ip(
    address = ip
)

fun Ip.toParcelableIp() = DeserializedIp(
    address = address
)