package my.dahr.monopolyone.ui.login

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.network.dto.response.TotpResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession
import my.dahr.monopolyone.workers.SessionWorker
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceRepository: ResourceRepository,
    private val sessionHelper: SessionHelper
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _requestStatusLiveData = MutableLiveData<RequestStatus>()
    val requestStatusLiveData: LiveData<RequestStatus> get() = _requestStatusLiveData

    lateinit var totpToken: String

    fun signIn(email: String, password: String, appContext: Context) {

        _requestStatusLiveData.postValue(RequestStatus.Loading)

        if (sessionHelper.haveInternetConnection()) {
            viewModelScope.launch(coroutineContext) {
                loginRepository.postSignIn(
                    email,
                    password,
                    object : MonopolyCallback<BaseResponse>(_requestStatusLiveData, null) {
                        override fun onSuccessfulResponse(
                            call: Call<BaseResponse>, responseBody: BaseResponse
                        ) {
                            when (responseBody) {
                                is SessionResponse -> {
                                    val sessionResponse = responseBody.`data`
                                    sessionHelper.session = sessionResponse.toSession().also {
                                        SessionWorker.enqueue(it.expiresAt, appContext)
                                    }
                                    _requestStatusLiveData.postValue(RequestStatus.Success)
                                }

                                is TotpResponse -> {
                                    totpToken = responseBody.`data`.totpSessionToken
                                    _requestStatusLiveData.postValue(RequestStatus.TwoFaCode)
                                }

                                else -> handleErrorResponse(responseBody)
                            }
                        }

                    }
                )
            }
        } else {
            _requestStatusLiveData.postValue(RequestStatus.NoInternetConnection)
        }


    }

    fun loadBitmap(@DrawableRes id: Int) = resourceRepository.getBitmapFromDrawableRes(id)
    fun loadErrorMessage(status: RequestStatus) =
        resourceRepository.getErrorMessageStringResource(status)


}