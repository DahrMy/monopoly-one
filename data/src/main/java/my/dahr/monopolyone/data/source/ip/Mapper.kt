package my.dahr.monopolyone.data.source.ip

import my.dahr.monopolyone.data.source.ip.local.ParcelableIp
import my.dahr.monopolyone.data.source.ip.remote.MyIpResponse
import my.dahr.monopolyone.domain.model.session.Ip

fun ParcelableIp.toIp() = Ip(
    address = address
)

fun MyIpResponse.toIp() = Ip(
    address = ip
)