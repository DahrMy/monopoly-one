package my.dahr.monopolyone.ui.home.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.converters.toUi
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.models.Session
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.users.UsersResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.domain.models.users.Data
import my.dahr.monopolyone.domain.repository.UsersRepository
import my.dahr.monopolyone.utils.SESSION_KEY
import my.dahr.monopolyone.utils.SHARED_PREFERENCES
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val usersRepository: UsersRepository,
    private val resourceRepository: ResourceRepository
) : ViewModel(){
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val sessionJson = sharedPreferences.getString(SESSION_KEY, "")
    private var session = Gson().fromJson(sessionJson, Session::class.java)

    private var user: Data? = null

    val usersResultLiveData = MutableLiveData<List<Data>>()

    private val myCoroutineContext = SupervisorJob() + Dispatchers.IO

     val requestStatusLiveData = MutableLiveData<RequestStatus>()

    fun getListOfUsers() {
        requestStatusLiveData.postValue(RequestStatus.Loading)
        viewModelScope.launch(myCoroutineContext) {
            val response = usersRepository.getUsersList(
                session.userId, setOf(1), "full", callback = object : MonopolyCallback<BaseResponse>(requestStatusLiveData, null){
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
            Log.d("user", response.toString())
        }
    }
    fun setUser(receivedUser: Data){
        user = receivedUser
    }

    fun getUser(): Data? {
        return user
    }

    fun loadErrorMessage(status: RequestStatus) =
        resourceRepository.getErrorMessageStringResource(status)
}