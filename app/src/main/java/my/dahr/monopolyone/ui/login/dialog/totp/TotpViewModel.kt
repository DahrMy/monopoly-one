package my.dahr.monopolyone.ui.login.dialog.totp

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MyRetrofitCallback
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.data.repository.ResourceRepository
import my.dahr.monopolyone.ui.login.LoginRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceRepository: ResourceRepository,
    private val sessionHelper: SessionHelper
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + Job()

    private val _requestStatusLiveData = MutableLiveData<RequestStatus>()
    val requestStatusLiveData: LiveData<RequestStatus> get() = _requestStatusLiveData

    fun verifyCode(code: Int, totpToken: String) {

        _requestStatusLiveData.postValue(RequestStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            loginRepository.verify2faCode(
                code,
                totpToken,
                object : MyRetrofitCallback<BaseResponse>(_requestStatusLiveData) {
                    override fun onSuccessfulResponse(
                        call: Call<BaseResponse>, responseBody: BaseResponse
                    ) {
                        if (responseBody is SessionResponse) {
                            val sessionResponse = responseBody.data
                            sessionHelper.session = sessionResponse.toSession()
                            _requestStatusLiveData.postValue(RequestStatus.Success)
                        } else {
                            handleErrorResponse(responseBody)
                        }
                    }

                }
            )
        }
    }

    fun loadBitmap(@DrawableRes id: Int) = resourceRepository.getBitmapFromDrawableRes(id)
    fun loadErrorMessage(code: Int) = resourceRepository.getErrorMessageStringResource(code)

}

