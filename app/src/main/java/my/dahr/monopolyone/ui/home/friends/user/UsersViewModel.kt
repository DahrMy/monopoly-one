package my.dahr.monopolyone.ui.home.friends.user

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
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.users.UsersResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.domain.models.users.Data
import my.dahr.monopolyone.domain.repository.UsersRepository
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel() {

    private var user: Data? = null

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

    val usersResultLiveData = MutableLiveData<List<Data>>()

    val requestStatusLiveData = MutableLiveData<RequestStatus>()


    fun getListOfUsers(userId: Any) {
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch(myCoroutineContext) {
            usersRepository.getUsersList(
                userId,
                setOf(1),
                "full",
                callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>,
                        responseBody: BaseResponse
                    ) {
                        when (responseBody) {
                            is UsersResponse -> {
                                val users = responseBody.toUi().data
                                usersResultLiveData.postValue(users)
                                requestStatusLiveData.postValue(RequestStatus.Success)
                            }

                            else -> handleErrorResponse(responseBody)
                        }
                    }

                }
            )
        }
    }

    fun setUser(receivedUser: Data) {
        user = receivedUser
    }

    fun getUser(): Data? {
        return user
    }

    fun loadErrorMessage(status: RequestStatus) =
        resourceRepository.getErrorMessageStringResource(status)
}