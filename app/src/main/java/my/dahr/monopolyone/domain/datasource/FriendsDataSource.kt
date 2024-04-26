package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.domain.models.Friend
import my.dahr.monopolyone.domain.models.Friends

interface FriendsDataSource {
    suspend fun getFriendsList(
        userId: Any,
         online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int
    ):List<Friend>
}