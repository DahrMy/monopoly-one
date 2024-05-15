package my.dahr.monopolyone.data.network.datasource

import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.network.api.UsersApi
import my.dahr.monopolyone.domain.datasource.UsersDataSource
import my.dahr.monopolyone.domain.models.users.Data
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersDataSource {
    override suspend fun getUsersList(userId: Any, userIds: Set<Int>, type: String): List<Data> {
        return usersApi.getUsersList(userId, userIds, type).toUi().data
    }
}