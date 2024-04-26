package my.dahr.monopolyone.ui.home.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.models.Friend
import my.dahr.monopolyone.domain.repository.FriendsRepository
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository
) : ViewModel() {
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO
    val friendsResultLiveData = MutableLiveData<List<Friend>>()

    fun getFriendList() {
        viewModelScope.launch(myCoroutineContext) {
            val response = repository.getFriendsList("dahr_my", false, false, "short", 0, 20)
            friendsResultLiveData.postValue(response)
        }
    }
}



