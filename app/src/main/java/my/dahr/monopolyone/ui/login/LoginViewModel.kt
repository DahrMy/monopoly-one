package my.dahr.monopolyone.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.SuccessfulReturnable
import my.dahr.monopolyone.domain.model.login.LoginInputData
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.usecase.login.SignInUseCase
import my.dahr.monopolyone.utils.ProgressState
import my.dahr.monopolyone.workers.SessionWorker
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {


    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _loginResultLiveData = MutableLiveData<Returnable>()
    internal val loginResultLiveData: LiveData<Returnable> = _loginResultLiveData

    private val _loginProgressStateLiveData = MutableLiveData(ProgressState.Idle)
    internal val loginProgressStateLiveData: LiveData<ProgressState> = _loginProgressStateLiveData


    fun signIn(email: String, password: String, appContext: Context) {

        _loginProgressStateLiveData.postValue(ProgressState.Loading)

        val inputData = LoginInputData(email, password)

        viewModelScope.launch(coroutineContext) {
            val result = signInUseCase(inputData)
            _loginResultLiveData.postValue(result)

            when (result) {
                is SuccessfulReturnable -> {
                    _loginProgressStateLiveData.postValue(ProgressState.Success)

                    if (result is Session) {
                        SessionWorker.enqueue(result.expiresAt, appContext)
                    }
                }

                else /* is WrongReturnable */ -> {
                    _loginProgressStateLiveData.postValue(ProgressState.Error)
                }
            }


        }

    }

}