package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.domain.datasource.UsersDataSource
import my.dahr.monopolyone.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDataSource: UsersDataSource
) : UsersRepository {
    override fun getUsersList(userId: Any, userIds: Set<Int>, type: String, callback: MonopolyCallback<BaseResponse>) =
        usersDataSource.getUsersList(userId, userIds, type, callback)
}