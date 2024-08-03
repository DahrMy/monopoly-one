package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.auth.local.SessionLocalDataSource
import my.dahr.monopolyone.data.source.auth.remote.SessionRemoteDataSource
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse
import my.dahr.monopolyone.data.source.auth.toAuthSignInRequest
import my.dahr.monopolyone.data.source.auth.toSession
import my.dahr.monopolyone.data.source.auth.toTotpToken
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
    private val sessionLocalDataSource: SessionLocalDataSource
) : SessionRepository {

    override suspend fun getLoginOutputData(loginInputData: LoginInputData): Returnable =
        suspendCoroutine { continuation ->
            val request = loginInputData.toAuthSignInRequest()
            val call = sessionRemoteDataSource.signIn(request)
            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>, responseBody: BaseResponse
                ) {
                    when (responseBody) {
                        is SessionResponse -> continuation.resume(responseBody.toSession())
                        is TotpResponse -> continuation.resume(responseBody.toTotpToken())
                    }
                }

            })
        }

    override suspend fun getSession(totpInputData: TotpInputData): Returnable {
        TODO("Not yet implemented")
    }

    override fun getStoredSession(): Session? {
        TODO("Not yet implemented")
    }

    override fun saveSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshSession(session: Session): Returnable {
        TODO("Not yet implemented")
    }

}
