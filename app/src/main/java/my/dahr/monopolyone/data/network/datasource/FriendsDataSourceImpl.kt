package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.data.network.dto.friends.add.AddResponseJson
import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import javax.inject.Inject

class FriendsDataSourceImpl @Inject constructor(
    private val friendsApi: FriendsApi
) : FriendsDataSource {

    override suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int
    ): List<Friend> {
        return friendsApi.getFriendsList(userId, online, addUser, type, offset, count)
            .toUi()
            .data
            .friends
    }

    override suspend fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int
    ): List<Request> {
        return friendsApi.getFriendsRequestsList(accessToken, type, offset, count)
            .toUi()
            .data
            .requests
    }

    override suspend fun addFriend(response: AddResponseJson) {
        friendsApi.addFriend(response)
    }
}