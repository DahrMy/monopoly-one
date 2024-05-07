package my.dahr.monopolyone.ui.login

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.data.models.LoginStatus
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

    private val mLoginStatusLiveData = MutableLiveData<LoginStatus>()

    fun signIn(email: String, password: String) {

        mLoginStatusLiveData.postValue(LoginStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            repository.postSignIn(email, password, object : Callback<SessionResponse> {

                override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {
                    if (response.isSuccessful) {
                        val session = response.body()?.data?.toSession()
                        if (session != null) {
                            repository.saveSession(session)
                            mLoginStatusLiveData.postValue(LoginStatus.Success)
                        }
                    } else {
                        Log.d("Retrofit", "Error: ${response.body()?.data}")
                        mLoginStatusLiveData.postValue(LoginStatus.Failure)
                    }

                }

                override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                    Log.d("Retrofit", "Failure: ${t.message}")
                    mLoginStatusLiveData.postValue(LoginStatus.Failure)
                }

            })
        }

    }

    fun loadBitmap(@DrawableRes id: Int) = repository.getBitmapFromDrawableRes(id)

    val loginStatusLiveData: LiveData<LoginStatus> get() = mLoginStatusLiveData

}