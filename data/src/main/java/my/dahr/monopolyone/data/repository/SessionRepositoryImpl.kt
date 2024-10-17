package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.auth.local.SessionLocalDataSource
import my.dahr.monopolyone.data.source.auth.remote.SessionRemoteDataSource
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse
import my.dahr.monopolyone.data.source.auth.toDeserialized
import my.dahr.monopolyone.data.source.auth.toRequest
import my.dahr.monopolyone.data.source.auth.toSession
import my.dahr.monopolyone.data.source.auth.toTotpToken
import my.dahr.monopolyone.data.source.internet.NetworkStateDataSource
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.model.login.TotpInputData
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.repository.SessionRepository
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SessionRepositoryImpl(
    private val sessionRemoteDataSource: SessionRemoteDataSource,
    private val sessionLocalDataSource: SessionLocalDataSource,
    private val networkStateDataSource: NetworkStateDataSource
) : SessionRepository {


    override suspend fun getLoginOutputData(
        loginInputData: LoginInputData
    ): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->

            val request = loginInputData.toRequest()
            val call = sessionRemoteDataSource.signIn(request)

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(call: Call<BaseResponse>, responseBody: BaseResponse) {
                    when (responseBody) {
                        is SessionResponse -> continuation.resume(responseBody.toSession())
                        is TotpResponse -> continuation.resume(responseBody.toTotpToken())
                        else -> handleResponse(responseBody)
                    }
                }
            })
        }
    }


    override suspend fun getSession(
        totpInputData: TotpInputData
    ): Returnable = suspendCoroutine { continuation ->

        val request = totpInputData.toRequest()
        val call = sessionRemoteDataSource.verifyTotp(request)

        call.enqueue(object : MonopolyCallback(continuation) {
            override fun onSuccessfulResponse(call: Call<BaseResponse>, responseBody: BaseResponse) {
                if (responseBody is SessionResponse) {
                   continuation.resume(responseBody.toSession())
                } else {
                    handleResponse(responseBody)
                }
            }
        })

    }


    override suspend fun refreshSession(
        session: Session
    ): Returnable = suspendCoroutine { continuation ->

        val request = session.refreshToken.toRequest()
        val call = sessionRemoteDataSource.refreshSession(request)

        call.enqueue(object : MonopolyCallback(continuation) {
            override fun onSuccessfulResponse(call: Call<BaseResponse>, responseBody: BaseResponse) {
                if (responseBody is SessionResponse) {
                    continuation.resume(responseBody.toSession())
                } else {
                    handleResponse(responseBody)
                }
            }
        })

    }


    override fun getStoredSession(): Session? =
        sessionLocalDataSource.deserializedSession?.toSession()


    override fun saveSession(session: Session) {
        sessionLocalDataSource.deserializedSession = session.toDeserialized()
    }


}
