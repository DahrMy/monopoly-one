package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.models.users.Data

interface UsersRepository {
    suspend fun getUsersList(
        userId: Any,
        userIds: Set<Int>,
        type: String
    ): List<Data>
}