package my.dahr.monopolyone.domain.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

interface UsersRepository {
    fun getUsersList(
        userId: Any,
        userIds: Set<Int>,
        type: String,
        callback: MonopolyCallback<BaseResponse>
    )
}