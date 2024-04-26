package my.dahr.monopolyone.data.converters

import my.dahr.monopolyone.data.network.dto.DataJson
import my.dahr.monopolyone.data.network.dto.FriendJson
import my.dahr.monopolyone.data.network.dto.FriendsResponse
import my.dahr.monopolyone.data.network.dto.RankJson
import my.dahr.monopolyone.domain.models.Data
import my.dahr.monopolyone.domain.models.Friend
import my.dahr.monopolyone.domain.models.Friends
import my.dahr.monopolyone.domain.models.Rank

fun RankJson.toUi(): Rank {
    return Rank(
        hidden = hidden ?: -1
    )
}

fun FriendJson.toUi(): Friend {
    return Friend(
        approved = approved ?: -1,
        avatar = avatar ?: "",
        avatarKey = avatarKey ?: "",
        domain = domain ?: "",
        games = games ?: -1,
        gamesWins = gamesWins ?: -1,
        gender = gender ?: -1,
        moderator = moderator ?: -1,
        nick = nick ?: "",
        nicksOld = nicksOld ?: listOf(),
        online = online ?: -1,
        profileCover = profileCover ?: "",
        rank = (rank ?: RankJson()).toUi(),
        superadmin = superadmin ?: -1,
        userId = userId ?: -1,
        vip = vip ?: -1,
        xp = xp ?: -1,
        xpLevel = xpLevel ?: -1
    )
}

fun DataJson.toUi(): Data {
    return Data(
        count = count ?: -1,
        friends = friends?.mapNotNull { it?.toUi() } ?: listOf()
    )
}
fun FriendsResponse.toUi(): Friends{
    return Friends(
        code = code ?: -1,
        data = (data ?: DataJson()).toUi()
    )
}