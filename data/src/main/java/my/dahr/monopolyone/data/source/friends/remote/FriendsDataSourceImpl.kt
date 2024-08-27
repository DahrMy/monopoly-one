package my.dahr.monopolyone.data.source.friends.remote

import my.dahr.monopolyone.data.network.api.monopoly.FriendsApi
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteParamsData
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.friends.remote.dto.request.list.ListParamsData
import my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests.RequestsParamsData
import retrofit2.Call

class FriendsDataSourceImpl(private val friendsApi: FriendsApi) : FriendsDataSource {
    override fun getListOfFriends(listParamsData: ListParamsData): Call<BaseResponse> =
        friendsApi.getFriendsList(listParamsData)

    override fun getListOfRequests(requestsParamsData: RequestsParamsData): Call<BaseResponse> =
        friendsApi.getFriendsRequestsList(requestsParamsData)

    override fun addOrAcceptUser(addParamsData: AddParamsData): Call<BaseResponse> =
        friendsApi.addFriend(addParamsData)

    override fun deleteOrDeclineUser(deleteParamsData: DeleteParamsData): Call<BaseResponse> =
        friendsApi.deleteFriend(deleteParamsData)
}