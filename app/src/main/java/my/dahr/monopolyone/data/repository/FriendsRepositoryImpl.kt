package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponseJson
import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import my.dahr.monopolyone.domain.repository.FriendsRepository
import retrofit2.Callback
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendsDataSource: FriendsDataSource
) : FriendsRepository {
    override fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    )= friendsDataSource.getFriendsList(userId, online, addUser, type, offset, count, callback)

    override fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    )= friendsDataSource.getFriendsRequestsList(accessToken, type, offset, count, callback)

    override fun addFriend( addRequest: AddRequest, callback: Callback<BaseResponse>) =
        friendsDataSource.addFriend(addRequest, callback)

    override suspend fun deleteFriend(response: DeleteResponseJson) =
        friendsDataSource.deleteFriends(response)

}