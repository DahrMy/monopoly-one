package my.dahr.monopolyone.ui.inventory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.model.inventory.params.ItemParams
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.models.inventory.items.Inventory
import my.dahr.monopolyone.domain.models.inventory.items.Item
import my.dahr.monopolyone.domain.usecase.inventory.GetListOfItemsUseCase
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val requireSessionUseCase: RequireSessionUseCase,
    private val getListOfItemsUseCase: GetListOfItemsUseCase
) : ViewModel() {
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val itemsResultLiveData = MutableLiveData<List<Item>>()

    fun getItemList() {
        viewModelScope.launch(myCoroutineContext) {
            val session = requireSessionUseCase()
            if (session is Session){
                val params = ItemParams(
                   accessToken =  session.accessToken.token,
                    userId = "dahr_my",
                    includeStock = true,
                    order = "time",
                    count = Int.MAX_VALUE,
                    addUser = false,
                    addEquipped = "array",
                    addLegacy = false,
                )
                val listOfItems = getListOfItemsUseCase(params)
                if (listOfItems is Inventory){
                    val result = listOfItems.data.items
                    itemsResultLiveData.postValue(result)
                }
            }
        }
    }
}