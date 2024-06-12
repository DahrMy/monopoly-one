package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

interface UsersDataSource {
    fun getUsersList(
        userId: Any,
        userIds: Set<Int>,
        type: String,
        callback: MonopolyCallback<BaseResponse>
    )
}