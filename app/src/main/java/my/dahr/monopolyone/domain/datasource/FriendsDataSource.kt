package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.data.network.dto.response.friends.add.AddResponseJson
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponseJson
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request

interface FriendsDataSource {
    suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int
    ): List<Friend>

    suspend fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
    ): List<Request>

    suspend fun addFriend(
        response: AddResponseJson
    )

    suspend fun deleteFriends(
        response: DeleteResponseJson
    )
}