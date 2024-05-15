package my.dahr.monopolyone.ui.home.friends

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import my.dahr.monopolyone.domain.repository.UsersRepository
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    fun getListOfUsers(userId: Any) {
        viewModelScope.launch(myCoroutineContext) {
            val response = usersRepository.getUsersList(
                userId, setOf(1), "short"
            )
            Log.d("users", response.toString())
        }
    }
}