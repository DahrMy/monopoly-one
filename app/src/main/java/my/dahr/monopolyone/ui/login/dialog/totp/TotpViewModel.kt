package my.dahr.monopolyone.ui.login.dialog.totp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.dto.response.ErrorResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.ui.login.LoginRepository
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.toSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val sessionHelper: SessionHelper
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + Job()

    private val _requestStatusLiveData = MutableLiveData<RequestStatus>()
    val requestStatusLiveData: LiveData<RequestStatus> get() = _requestStatusLiveData

    fun verifyCode(code: Int, totpToken: String) {

        _requestStatusLiveData.postValue(RequestStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            repository.verify2faCode(code, totpToken) { object : Callback<SessionResponse> {

                override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()

                        if (responseBody != null) {
                            val sessionResponse = responseBody.data
                            sessionHelper.session = sessionResponse.toSession()
                            _requestStatusLiveData.postValue(RequestStatus.Success)
                        }

                    } else {

                        val errorJson = response.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

                        Log.e("Retrofit", "Error:\n$errorJson")

                        val status = if (errorResponse != null) {
                            RequestStatus.entries.find { status ->
                                status.code == errorResponse.code
                            } ?: RequestStatus.UndefinedError
                        } else {
                            RequestStatus.UndefinedError
                        }

                        _requestStatusLiveData.postValue(status)

                    }

                }

                override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                    Log.e("Retrofit", "Failure: ${t.message}")
                    _requestStatusLiveData.postValue(RequestStatus.Failure)
                }

            }}

        }

    }

}