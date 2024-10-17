package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.users.UsersResponse
import my.dahr.monopolyone.data.source.internet.NetworkStateDataSource
import my.dahr.monopolyone.data.source.user.remote.UserDataSource
import my.dahr.monopolyone.data.source.user.toRequest
import my.dahr.monopolyone.data.source.user.toUsers
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.user.params.UserParams
import my.dahr.monopolyone.domain.repository.UserRepository
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl (
    private val userDataSource: UserDataSource,
    private val networkStateDataSource: NetworkStateDataSource
): UserRepository {

    override suspend fun getUsersList(userParams: UserParams): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->
            val request = userParams.toRequest()
            val call = userDataSource.getUsersList(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is UsersResponse) {
                        continuation.resume(responseBody.toUsers())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }


}