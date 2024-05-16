package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import my.dahr.monopolyone.domain.repository.UsersRepository
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.models.friends.list.Friend
import my.dahr.monopolyone.domain.models.users.Data

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private var user: Data? = null

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val usersResultLiveData = MutableLiveData<List<Data>>()

    fun getListOfUsers(userId: Any) {
        viewModelScope.launch(myCoroutineContext) {
            val response = usersRepository.getUsersList(
                userId, setOf(1), "full"
            )
            usersResultLiveData.postValue(response)
        }
    }

    fun setUser(receivedUser: Data){
        user = receivedUser
    }

    fun getUser(): Data? {
        return user
    }

}