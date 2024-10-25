package my.dahr.monopolyone.data.source.friends.remote

import my.dahr.monopolyone.data.network.api.monopoly.FriendsApi
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteParamsData
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import retrofit2.Call

class FriendsDataSourceImpl(private val friendsApi: FriendsApi) : FriendsDataSource {
    override fun getListOfFriends(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
    ): Call<BaseResponse> =
        friendsApi.getFriendsList(userId, online, addUser, type, offset, count)

    override fun getListOfRequests(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
    ): Call<BaseResponse> =
        friendsApi.getFriendsRequestsList(accessToken, type, offset, count)

    override fun addOrAcceptUser(addParamsData: AddParamsData): Call<BaseResponse> =
        friendsApi.addFriend(addParamsData)

    override fun deleteOrDeclineUser(deleteParamsData: DeleteParamsData): Call<BaseResponse> =
        friendsApi.deleteFriend(deleteParamsData)
}