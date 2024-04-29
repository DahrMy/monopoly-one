package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.models.friends.list.Friend
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
    fun getFriendRequestsList(){
        viewModelScope.launch (myCoroutineContext){
            try {
                val response = repository.getFriendsRequestsList("short", 0, 20)
            }
            catch (e: Exception){
                Log.d("error", e.toString())
            }
        }
    }
}



