package my.dahr.monopolyone.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class MyRetrofitCallback<T>(
    private val requestStatusLiveData: MutableLiveData<RequestStatus>
) : Callback<T> {

    abstract fun onSuccessfulResponse(call: Call<T>, responseBody: T)

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
        requestStatusLiveData.postValue(RequestStatus.Failure)
    }

    protected fun handleErrorResponse(response: BaseResponse?) {

        Log.e("Retrofit", "Error:\n$response")

        val status = if (response != null) {
            RequestStatus.entries.find { status ->
                status.code == response.code
            } ?: RequestStatus.UndefinedError
        } else {
            RequestStatus.UndefinedError
        }

        requestStatusLiveData.postValue(status)

    }

}