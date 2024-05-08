package my.dahr.monopolyone.ui.login

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.dto.response.ErrorResponse
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.utils.toSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val mRequestStatusLiveData = MutableLiveData<RequestStatus>()

    fun signIn(email: String, password: String) {

        mRequestStatusLiveData.postValue(RequestStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            repository.postSignIn(email, password) { object : Callback<SessionResponse> {

                override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                    if (response.isSuccessful) {
                        val session = response.body()?.data?.toSession()
                        if (session != null) {
                            repository.saveSession(session)
                            mRequestStatusLiveData.postValue(RequestStatus.Success)
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

                        mRequestStatusLiveData.postValue(status)
                    }

                }

                override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                    Log.e("Retrofit", "Failure: ${t.message}")
                    mRequestStatusLiveData.postValue(RequestStatus.Failure)
                }

            } }
        }

    }

    fun loadBitmap(@DrawableRes id: Int) = repository.getBitmapFromDrawableRes(id)
    fun loadErrorMessage(code: Int) = repository.getErrorMessageStringResource(code)

    val requestStatusLiveData: LiveData<RequestStatus> get() = mRequestStatusLiveData

}