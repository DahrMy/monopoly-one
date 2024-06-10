package my.dahr.monopolyone.ui.home.inventory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.models.inventory.Item
import my.dahr.monopolyone.domain.repository.InventoryRepository
import my.dahr.monopolyone.utils.SessionHelper
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val sessionHelper: SessionHelper,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val itemsResultLiveData = MutableLiveData<List<Item>>()

    fun getItemList() {
        val sessionFromHelper = sessionHelper.session
        if (sessionFromHelper != null) {
            viewModelScope.launch(myCoroutineContext) {
                try {
                    val response = inventoryRepository.getInventoryList(
                        sessionFromHelper.accessToken,
                        "dahr_my",
                        includeStock = true,
                        order = "time",
                        count = Int.MAX_VALUE,
                        addUser = false,
                        addEquipped = "array",
                        addLegacy = false,
                    )
                    itemsResultLiveData.postValue(response)
                    Log.d("result", response.toString())
                } catch (e: HttpException) {
                    if (e.code() == 428) {
                        Log.e("error", e.toString())

                    }
                }
            }
        }

    }
}