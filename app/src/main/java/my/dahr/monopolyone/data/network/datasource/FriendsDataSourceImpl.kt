package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteRequest
import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import retrofit2.Callback
import javax.inject.Inject

class FriendsDataSourceImpl @Inject constructor(
    private val friendsApi: FriendsApi
) : FriendsDataSource {
    override fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    ) {
        friendsApi.getFriendsList(userId, online, addUser, type, offset, count).enqueue(callback)
    }

    override fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    ) {
        friendsApi.getFriendsRequestsList(accessToken, type, offset, count).enqueue(callback)
    }

    override fun addFriend(addRequest: AddRequest, callback: Callback<BaseResponse>) {
        friendsApi.addFriend(addRequest).enqueue(callback)
    }

    override fun deleteFriends(deleteRequest: DeleteRequest, callback: Callback<BaseResponse>) {
        friendsApi.deleteFriend(deleteRequest).enqueue(callback)
    }
}