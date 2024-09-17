package my.dahr.monopolyone.ui.dialog.totp

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
import my.dahr.monopolyone.domain.model.login.TotpInputData
import my.dahr.monopolyone.domain.model.login.TotpToken
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.usecase.login.VerifyTotpUseCase
import my.dahr.monopolyone.utils.ProgressState
import my.dahr.monopolyone.workers.SessionWorker
import javax.inject.Inject

@HiltViewModel
class TotpViewModel @Inject constructor(
    private val verifyTotpUseCase: VerifyTotpUseCase
) : ViewModel() {


    private val coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _totpVerifyResultLiveData = MutableLiveData<Returnable>()
    internal val totpVerifyResultLiveData: LiveData<Returnable> = _totpVerifyResultLiveData

    private val _totpVerifyProgressStateLiveData = MutableLiveData(ProgressState.Idle)
    internal val totpVerifyProgressStateLiveData: LiveData<ProgressState> =
        _totpVerifyProgressStateLiveData


    fun verifyCode(code: Int, totpToken: TotpToken, appContext: Context) {

        _totpVerifyProgressStateLiveData.postValue(ProgressState.Loading)

        val inputData = TotpInputData(totpToken, code)

        viewModelScope.launch(coroutineContext) {
            val result = verifyTotpUseCase(inputData)
            _totpVerifyResultLiveData.postValue(result)

            when (result) {
                is Session -> {
                    _totpVerifyProgressStateLiveData.postValue(ProgressState.Success)
                    SessionWorker.enqueue(result.expiresAt, appContext)
                }

                else /* is WrongReturnable */ -> {
                    _totpVerifyProgressStateLiveData.postValue(ProgressState.Error)
                }
            }
        }

    }


}

