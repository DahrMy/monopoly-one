package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.api.UsersApi
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.domain.datasource.UsersDataSource
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersDataSource {
    override fun getUsersList(userId: Any, userIds: Set<Int>, type: String, callback: MonopolyCallback<BaseResponse>) {
        return usersApi.getUsersList(userId, userIds, type).enqueue(callback)
    }
}