package my.dahr.monopolyone.data.network.api.monopoly

import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthRefreshRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthSignInRequest
import my.dahr.monopolyone.data.source.auth.remote.dto.request.AuthTotpVerifyRequest
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.auth.remote.dto.response.SessionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    /**
     * @see <a href="https://docs.mnpl.one/api/http/auth.signin">auth.signin</a>
     */
    @POST("auth.signin")
    fun authSignIn(@Body body: AuthSignInRequest): Call<BaseResponse>

    /**
     * @see <a href="https://docs.mnpl.one/api/http/auth.totpVerify">auth.totpVerify</a>
     */
    @POST("auth.totpVerify")
    fun authTotpVerify(@Body body: AuthTotpVerifyRequest): Call<BaseResponse>

    /**
     * @see <a href="https://docs.mnpl.one/api/http/auth.refresh">auth.refresh</a>
     */
    @POST("auth.refresh")
    fun authRefresh(@Body body: AuthRefreshRequest): Call<SessionResponse>

}