package my.dahr.monopolyone.data.source.friends

import my.dahr.monopolyone.data.network.dto.response.friends.add.AddParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.list.DataJson
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendJson
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.RankJson
import my.dahr.monopolyone.data.network.dto.response.friends.requests.DataRequestsJson
import my.dahr.monopolyone.data.network.dto.response.friends.requests.FriendsRequestsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.requests.RankRequestsJson
import my.dahr.monopolyone.data.network.dto.response.friends.requests.RequestJson
import my.dahr.monopolyone.data.source.friends.remote.dto.request.list.ListParamsData
import my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests.RequestsParamsData
import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.model.friends.params.DeleteParams
import my.dahr.monopolyone.domain.model.friends.params.ListParams
import my.dahr.monopolyone.domain.model.friends.params.RequestsParams
import my.dahr.monopolyone.domain.models.friends.list.Data
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.list.Friends
import my.dahr.monopolyone.domain.models.friends.list.Rank
import my.dahr.monopolyone.domain.models.friends.requests.DataRequests
import my.dahr.monopolyone.domain.models.friends.requests.FriendsRequests
import my.dahr.monopolyone.domain.models.friends.requests.RankRequests
import my.dahr.monopolyone.domain.models.friends.requests.Request

fun ListParams.toRequest() = ListParamsData(
    userId = userId,
    online = online,
    addUser = addUser,
    type = type,
    offset = offset,
    count = count
)

fun RequestsParams.toRequest() = RequestsParamsData(
    accessToken = accessToken,
    type = type,
    offset = offset,
     count = count
)

fun AddParams.toRequest() = AddParamsData(
    access_token = access_token,
    userId = userId
)

fun DeleteParams.toRequest() = DeleteParamsData(
    access_token = access_token,
    userId = userId
)

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

fun FriendsResponse.toFriend(): Friends {
    return Friends(
        code = code ?: -1,
        data = (data ?: DataJson()).toUi()
    )
}

fun DataRequestsJson.toUi(): DataRequests {
    return DataRequests(
        count = count ?: -1,
        requests = requests?.mapNotNull { it?.toUi() } ?: listOf()
    )
}

fun FriendsRequestsResponse.toFriend(): FriendsRequests {
    return FriendsRequests(
        code = code ?: -1,
        data = (data ?: DataRequestsJson()).toUi()
    )
}

fun RankRequestsJson.toUi(): RankRequests {
    return RankRequests(
        hidden = hidden ?: -1
    )
}

fun RequestJson.toUi(): Request {
    return Request(
        avatar = avatar ?: "",
        friendship = friendship ?: -1,
        games = games ?: -1,
        gamesWins = gamesWins ?: -1,
        gender = gender ?: -1,
        nick = nick ?: "",
        nicksOld = nicksOld ?: listOf(),
        rank = (rank ?: RankRequestsJson()).toUi(),
        userId = userId ?: -1,
        xp = xp ?: -1,
        xpLevel = xpLevel ?: -1,
    )
}