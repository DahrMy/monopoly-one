package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.domain.datasource.UsersDataSource
import my.dahr.monopolyone.domain.models.users.Data
import my.dahr.monopolyone.domain.repository.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDataSource: UsersDataSource
) : UsersRepository {
    override suspend fun getUsersList(userId: Any, userIds: Set<Int>, type: String): List<Data> =
        usersDataSource.getUsersList(userId, userIds, type)
}