package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddRequest
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponseJson
import retrofit2.Callback

interface FriendsDataSource {
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

    suspend fun deleteFriends(
        response: DeleteResponseJson
    )
}