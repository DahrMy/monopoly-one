package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.model.friends.params.DeleteParams
import my.dahr.monopolyone.domain.model.friends.params.ListParams
import my.dahr.monopolyone.domain.model.friends.params.RequestsParams

interface FriendsRepository {
    suspend fun getFriendsList(
        friendsParams: ListParams
    ): Returnable

    suspend fun getFriendsRequestsList(
        requestsParams: RequestsParams
    ): Returnable

    suspend fun addFriend(
        addParams: AddParams,
    ): Returnable

    suspend fun deleteFriend(
        deleteParams: DeleteParams,
    ): Returnable
}