package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.friends.list.FriendsResponse
import my.dahr.monopolyone.data.network.dto.response.friends.requests.FriendsRequestsResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.friends.remote.FriendsDataSource
import my.dahr.monopolyone.data.source.friends.toFriend
import my.dahr.monopolyone.data.source.friends.toRequest
import my.dahr.monopolyone.data.source.internet.NetworkStateDataSource
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.model.friends.params.DeleteParams
import my.dahr.monopolyone.domain.model.friends.params.ListParams
import my.dahr.monopolyone.domain.model.friends.params.RequestsParams
import my.dahr.monopolyone.domain.repository.FriendsRepository
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FriendsRepositoryImpl (
    private val friendsDataSource: FriendsDataSource,
    private val networkStateDataSource: NetworkStateDataSource
): FriendsRepository {


    override suspend fun getFriendsList(
        friendsParams: ListParams
    ): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->
            val request = friendsParams.toRequest()
            val call = friendsDataSource.getListOfFriends(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is FriendsResponse) {
                        continuation.resume(responseBody.toFriend())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }


    override suspend fun getFriendsRequestsList(
        requestsParams: RequestsParams
    ): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->
            val request = requestsParams.toRequest()
            val call = friendsDataSource.getListOfRequests(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is FriendsRequestsResponse) {
                        continuation.resume(responseBody.toFriend())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }


    override suspend fun addFriend(addParams: AddParams): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->
            val request = addParams.toRequest()
            val call = friendsDataSource.addOrAcceptUser(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is FriendsResponse) {
                        continuation.resume(responseBody.toFriend())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }


    override suspend fun deleteFriend(deleteParams: DeleteParams): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->

            val request = deleteParams.toRequest()
            val call = friendsDataSource.deleteOrDeclineUser(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is FriendsResponse) {
                        continuation.resume(responseBody.toFriend())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }


}