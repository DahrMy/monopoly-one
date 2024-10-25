package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.model.friends.params.DeleteParams

interface FriendsRepository {
    suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
    ): Returnable

    suspend fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
    ): Returnable

    suspend fun addFriend(
        addParams: AddParams,
    ): Returnable

    suspend fun deleteFriend(
        deleteParams: DeleteParams,
    ): Returnable
}