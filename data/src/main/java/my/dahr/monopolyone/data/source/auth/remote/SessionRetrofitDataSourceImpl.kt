package my.dahr.monopolyone.data.source.auth.remote

import my.dahr.monopolyone.data.network.api.monopoly.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthRefreshRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthSignInRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthTotpVerifyRequest
import retrofit2.Call

internal class SessionRetrofitDataSourceImpl(
    private val api: AuthorizationApi
) : SessionRemoteDataSource {

    override fun signIn(request: AuthSignInRequest): Call<BaseResponse> =
        api.authSignIn(request)

    override fun verifyTotp(request: AuthTotpVerifyRequest): Call<BaseResponse> =
        api.authTotpVerify(request)

    override fun refreshSession(request: AuthRefreshRequest): Call<BaseResponse> =
        api.authRefresh(request)

}