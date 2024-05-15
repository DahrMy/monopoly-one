package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.domain.models.users.Data

interface UsersDataSource {
    suspend fun getUsersList(
        userId: Any,
        userIds: Set<Int>,
        type: String
    ): List<Data>
}