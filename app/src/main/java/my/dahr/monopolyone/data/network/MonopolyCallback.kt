package my.dahr.monopolyone.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class MonopolyCallback<T>(
    private val requestStatusLiveData: MutableLiveData<RequestStatus>?,
    private val requestStatusFlow: MutableSharedFlow<RequestStatus>?
) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {

            val responseBody = response.body()

            if (responseBody != null) {
                onSuccessfulResponse(call, responseBody)
            } else {
                handleErrorResponse(null)
            }

        } else {
            val jsonResponse = response.errorBody()?.string()
            val responseBody = Gson().fromJson(jsonResponse, BaseResponse::class.java)
            handleErrorResponse(responseBody)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e("Retrofit", "Failure: ${t.message}")
        Log.e("Retrofit", "StackTrace: ${t.stackTraceToString()}")
        postStatus(RequestStatus.Failure)
    }

    open fun onSuccessfulResponse(call: Call<T>, responseBody: T) {
        postStatus(RequestStatus.Success)
    }

    protected fun handleErrorResponse(response: BaseResponse?) {

        Log.e("Retrofit", "Error:\n${response.toString()}")

        val status = if (response != null) {
            RequestStatus.entries.find { status ->
                status.code == response.code
            } ?: RequestStatus.UndefinedError
        } else {
            RequestStatus.UndefinedError
        }

        postStatus(status)

    }

    private fun postStatus(status: RequestStatus) {
        when {
            requestStatusLiveData != null -> requestStatusLiveData.postValue(status)
            requestStatusFlow != null -> CoroutineScope(Dispatchers.Default).launch {
                requestStatusFlow.emit(status)
            }
            else -> throw Exception("requestStatusLiveData and requestStatusFlow both shouldn't be null")
        }
    }

}