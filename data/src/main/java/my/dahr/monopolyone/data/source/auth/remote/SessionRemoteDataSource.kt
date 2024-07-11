package my.dahr.monopolyone.data.source.auth.remote

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthSignInRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthTotpVerifyRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.TotpResponse
import retrofit2.Call

sealed interface SessionRemoteDataSource {
    /**
     * @return [Call] with [SessionResponse] or [TotpResponse],
     * or with [ErrorResponse] if an error is occurred. TODO: add ErrorResponse
     */
    fun signIn(request: AuthSignInRequest): Call<BaseResponse>

    /**
     * @return [Call] with [SessionResponse],
     * or with [ErrorResponse] if an error is occurred. TODO: add ErrorResponse
     */
    fun verifyTotp(request: AuthTotpVerifyRequest): Call<BaseResponse>
}