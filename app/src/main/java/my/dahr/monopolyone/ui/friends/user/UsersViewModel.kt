package my.dahr.monopolyone.ui.friends.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.model.user.info.Data
import my.dahr.monopolyone.domain.model.user.info.Users
import my.dahr.monopolyone.domain.model.user.params.UserParams
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import my.dahr.monopolyone.domain.usecase.user.FindUserUseCase
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val requireSessionUseCase: RequireSessionUseCase,
    private val findUserUseCase: FindUserUseCase
) : ViewModel() {

    //need to be refactored
    private var user: Data? = null

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val usersResultLiveData = MutableLiveData<List<Data>>()

    fun getListOfUsers(userId: Any) {
        viewModelScope.launch(myCoroutineContext) {
            val session = requireSessionUseCase()
            if (session is Session) {
                val params = UserParams(
                    userId,
                    setOf(1),
                    "full",
                )
                val user = findUserUseCase(params)
                if (user is Users) {
                    val result = user.data
                    usersResultLiveData.postValue(result)
                }
            }
        }
    }

    fun setUser(receivedUser: Data) {
        user = receivedUser
    }

    fun getUser(): Data? {
        return user
    }
}