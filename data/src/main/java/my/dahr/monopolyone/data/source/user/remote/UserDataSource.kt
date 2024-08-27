package my.dahr.monopolyone.data.source.user.remote

import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.user.remote.dto.request.UserParamsData
import retrofit2.Call

interface UserDataSource {

    fun getUsersList(userParamsData: UserParamsData): Call<BaseResponse>
}