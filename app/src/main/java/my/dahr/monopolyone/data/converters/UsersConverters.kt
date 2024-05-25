package my.dahr.monopolyone.data.converters

import my.dahr.monopolyone.data.network.dto.response.users.DataJson
import my.dahr.monopolyone.data.network.dto.response.users.RankJson
import my.dahr.monopolyone.data.network.dto.response.users.UsersResponse
import my.dahr.monopolyone.domain.models.users.Data
import my.dahr.monopolyone.domain.models.users.Rank
import my.dahr.monopolyone.domain.models.users.Users

fun RankJson.toUi(): Rank {
    return Rank(
        hidden = hidden ?: -1
    )
}

fun DataJson.toUi(): Data {
    return Data(
        approved = approved ?: -1,
        avatar = avatar ?: "",
        domain = domain ?: "",
        games = games ?: -1,
        gamesWins = gamesWins ?: -1,
        gender = gender ?: -1,
        nick = nick ?: "",
        nicksOld = nicksOld ?: listOf(),
        online = online ?: -1,
        rank = (rank ?: RankJson()).toUi(),
        userId = userId ?: -1,
        xp = xp ?: -1,
        xpLevel = xpLevel ?: -1
    )
}

fun UsersResponse.toUi(): Users {
    return Users(
        code = code ?: -1,
        data = data?.mapNotNull { it?.toUi() } ?: listOf()
    )
}

