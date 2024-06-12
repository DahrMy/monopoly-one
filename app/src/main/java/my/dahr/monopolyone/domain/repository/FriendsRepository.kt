package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteRequest
import retrofit2.Callback

interface FriendsRepository {
    fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    )

    fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<BaseResponse>
    )

    fun addFriend(
        addRequest: AddRequest,
        callback: Callback<BaseResponse>
    )

    fun deleteFriend(
        deleteRequest: DeleteRequest,
        callback: Callback<BaseResponse>
    )
}