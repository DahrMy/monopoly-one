package my.dahr.monopolyone.domain.datasource

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse

interface InventoryDataSource {
    fun getInventoryList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
        callback: MonopolyCallback<BaseResponse>
    )
}