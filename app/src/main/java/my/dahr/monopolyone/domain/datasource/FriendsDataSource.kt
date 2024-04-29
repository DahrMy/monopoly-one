package my.dahr.monopolyone.domain.datasource

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
        type: String,
        offset: Int,
        count: Int,
    ): List<Request>
}