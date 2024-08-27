package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.user.params.UserParams

interface UserRepository {
    suspend fun getUsersList(
        userParams: UserParams
    ): Returnable
}