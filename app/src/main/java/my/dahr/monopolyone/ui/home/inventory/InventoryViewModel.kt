package my.dahr.monopolyone.ui.home.inventory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.inventory.InventoryResponse
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.domain.models.inventory.Item
import my.dahr.monopolyone.domain.repository.InventoryRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val sessionHelper: SessionHelper,
    private val inventoryRepository: InventoryRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel() {
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val itemsResultLiveData = MutableLiveData<List<Item>>()

    val requestStatusLiveData = MutableLiveData<RequestStatus>()

    fun getList() {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            inventoryRepository.getInventoryList(
                sessionFromHelper.accessToken,
                "dahr_my",
                includeStock = true,
                order = "time",
                count = Int.MAX_VALUE,
                addUser = false,
                addEquipped = "array",
                addLegacy = false,
                callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is InventoryResponse -> {
                                val items = responseBody.toUi().data.items
                                itemsResultLiveData.postValue(items)
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }
                    }

                }
            )
        }
    }

    fun getItemList() {
        val sessionFromHelper = sessionHelper.session
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch(myCoroutineContext) {
            if (sessionHelper.isSessionNotExpired()) {
                if (sessionHelper.isCurrentIpChanged()) {
                    if (sessionFromHelper != null) {
                        getList()
                    }
                } else {
                    sessionHelper.refreshSavedIp()
                }
            } else {
                if (sessionFromHelper != null) {
                    sessionHelper.refreshSession(sessionFromHelper.refreshToken) {
                        object : Callback<SessionResponse> {
                            override fun onResponse(
                                call: Call<SessionResponse>,
                                response: Response<SessionResponse>
                            ) {
                                val session = response.body()?.data?.toSession()
                                if (session != null) {
                                    sessionHelper.session = session
                                }
                                getList()
                            }

                            override fun onFailure(
                                call: Call<SessionResponse>,
                                t: Throwable
                            ) {
                                Log.e("Retrofit", "Failure: ${t.message}")
                            }
                        }
                    }
                }
            }
        }
    }
    fun loadErrorMessage(status: RequestStatus) =
        resourceRepository.getErrorMessageStringResource(status)
}