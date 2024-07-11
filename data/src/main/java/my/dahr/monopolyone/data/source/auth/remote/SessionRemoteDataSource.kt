package my.dahr.monopolyone.data.source.auth.remote

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import retrofit2.Call

sealed interface SessionRemoteDataSource {
    /**
     * @return [Call] with [SessionResponse] or [TotpResponse],
     * or with [ErrorResponse] if an error is occurred. TODO: add ErrorResponse
     */
    fun signIn(): Call<BaseResponse>

    /**
     * @return [Call] with [SessionResponse],
     * or with [ErrorResponse] if an error is occurred. TODO: add ErrorResponse
     */
    fun verifyTotp(): Call<BaseResponse>
}