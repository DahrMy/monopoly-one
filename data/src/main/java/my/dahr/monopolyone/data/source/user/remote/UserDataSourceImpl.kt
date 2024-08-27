package my.dahr.monopolyone.data.source.user.remote

import my.dahr.monopolyone.data.network.api.monopoly.UsersApi
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.user.remote.dto.request.UserParamsData
import retrofit2.Call

class UserDataSourceImpl(private val usersApi: UsersApi): UserDataSource {
    override fun getUsersList(userParamsData: UserParamsData): Call<BaseResponse> =
        usersApi.getUsersList(userParamsData)
}