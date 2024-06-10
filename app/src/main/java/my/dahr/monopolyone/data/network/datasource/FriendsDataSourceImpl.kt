package my.dahr.monopolyone.data.network.datasource

import androidx.lifecycle.MutableLiveData
import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.data.network.dto.response.friends.add.AddResponseJson
import my.dahr.monopolyone.data.network.dto.response.friends.delete.DeleteResponseJson
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.friends.requests.Request
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class FriendsDataSourceImpl @Inject constructor(
    private val friendsApi: FriendsApi
) : FriendsDataSource {

    val _requestStatusLiveData = MutableLiveData<RequestStatus>()

    override suspend fun getFriendsList(
        userId: Any,
        online: Boolean,
        addUser: Boolean,
        type: String,
        offset: Int,
        count: Int,
        callback: Callback<FriendsResponse> // TODO: Attention
    ): List<Friend> {

        var list: List<Friend>

        friendsApi.getFriendsList(userId, online, addUser, type, offset, count).enqueue(callback)

        // Callback example
        object : MonopolyCallback<FriendsResponse>(_requestStatusLiveData) {
            override fun onSuccessfulResponse(
                call: Call<FriendsResponse>, responseBody: FriendsResponse
            ) {
                when (responseBody) {
                    is FriendsResponse -> {
                        responseBody.toUi().data.friends
                    }
                    else -> handleErrorResponse(responseBody)
                }
            }

        }

    }

    override suspend fun getFriendsRequestsList(
        accessToken: String,
        type: String,
        offset: Int,
        count: Int
    ): List<Request> {
        return friendsApi.getFriendsRequestsList(accessToken, type, offset, count)
            .toUi()
            .data
            .requests
    }

    override suspend fun addFriend(response: AddResponseJson) {
        friendsApi.addFriend(response)
    }

    override suspend fun deleteFriends(response: DeleteResponseJson) {
        friendsApi.deleteFriend(response)
    }
}