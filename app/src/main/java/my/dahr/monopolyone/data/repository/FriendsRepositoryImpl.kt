package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import my.dahr.monopolyone.domain.repository.FriendsRepository
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendsDataSource: FriendsDataSource
):FriendsRepository {
    override suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int
    ): List<Friend> = friendsDataSource.getFriendsList(userId, online, addUser, type, offset, count)

    override suspend fun getFriendsRequestsList(
        type: String,
        offset: Int,
        count: Int
    ): List<Request> = friendsDataSource.getFriendsRequestsList(type,offset, count)

}