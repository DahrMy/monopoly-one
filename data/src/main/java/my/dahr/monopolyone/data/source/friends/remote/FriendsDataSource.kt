package my.dahr.monopolyone.data.source.friends.remote

import my.dahr.monopolyone.data.network.dto.response.friends.add.AddParamsData
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteParamsData
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.friends.remote.dto.request.list.ListParamsData
import my.dahr.monopolyone.data.source.friends.remote.dto.request.listrequests.RequestsParamsData
import retrofit2.Call

interface FriendsDataSource {
    fun getListOfFriends(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
    ): Call<BaseResponse>

    fun getListOfRequests(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
    ): Call<BaseResponse>

    fun addOrAcceptUser(addParamsData: AddParamsData): Call<BaseResponse>

    fun deleteOrDeclineUser(deleteParamsData: DeleteParamsData): Call<BaseResponse>
}