package my.dahr.monopolyone.data.repository

import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.inventory.items.InventoryResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.source.internet.NetworkStateDataSource
import my.dahr.monopolyone.data.source.inventory.remote.InventoryDataSource
import my.dahr.monopolyone.data.source.inventory.toInventory
import my.dahr.monopolyone.data.source.inventory.toRequest
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.inventory.params.ItemParams
import my.dahr.monopolyone.domain.repository.InventoryRepository
import retrofit2.Call
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class InventoryRepositoryImpl(
    private val inventoryDataSource: InventoryDataSource,
    private val networkStateDataSource: NetworkStateDataSource
) :
    InventoryRepository {

    override suspend fun getItemsList(
        accessToken: String,
        userId: Any,
        includeStock: Boolean,
        order: String,
        count: Int,
        addUser: Boolean,
        addEquipped: String,
        addLegacy: Boolean,
    ): Returnable {
        if (!networkStateDataSource.hasInternetConnection()) {
            return NoInternetConnectionError()
        }

        return suspendCoroutine { continuation ->

            val call = inventoryDataSource.getItemsList(
                accessToken,
                userId,
                includeStock,
                order,
                count,
                addUser,
                addEquipped,
                addLegacy
            )

            call.enqueue(object : MonopolyCallback(continuation) {
                override fun onSuccessfulResponse(
                    call: Call<BaseResponse>,
                    responseBody: BaseResponse
                ) {
                    if (responseBody is InventoryResponse) {
                        continuation.resume(responseBody.toInventory())
                    } else {
                        handleResponse(responseBody)
                    }
                }
            })
        }
    }
}
