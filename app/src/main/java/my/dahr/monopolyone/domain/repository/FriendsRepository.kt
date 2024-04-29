package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import retrofit2.http.Query

interface FriendsRepository {
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
    ) : List<Request>
}