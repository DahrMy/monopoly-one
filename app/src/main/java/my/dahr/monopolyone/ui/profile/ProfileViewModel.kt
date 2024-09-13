package my.dahr.monopolyone.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.model.user.info.Data
import my.dahr.monopolyone.domain.model.user.info.Users
import my.dahr.monopolyone.domain.model.user.params.UserParams
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import my.dahr.monopolyone.domain.usecase.user.FindUserUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val findUserUseCase: FindUserUseCase,
    private val requireSessionUseCase: RequireSessionUseCase
) : ViewModel() {

    private var user: Data? = null

    val usersResultLiveData = MutableLiveData<List<Data>>()

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    fun getListOfUsers() {
        viewModelScope.launch(myCoroutineContext) {
            val session = requireSessionUseCase()
            if (session is my.dahr.monopolyone.domain.model.session.Session) {
                val params = UserParams(
                    session.userId,
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