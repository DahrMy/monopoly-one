package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.models.Friend

interface FriendsRepository {
    suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int
    ) : List<Friend>
}