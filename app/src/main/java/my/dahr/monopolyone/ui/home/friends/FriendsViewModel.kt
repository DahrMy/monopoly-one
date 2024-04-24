package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository
): ViewModel(){
    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    fun getFriendList(){
        viewModelScope.launch(myCoroutineContext){
            val result = repository.getFriendsList()
            Log.d("Result", result.body().toString())
        }
    }

}