package my.dahr.monopolyone.data.network

import android.util.Log
import com.google.gson.Gson
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.BaseErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.toError
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.SuccessfulReturnable
import my.dahr.monopolyone.domain.model.UndefinedError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 * An adapted implementation of [Callback] interface for Monopoly One responses.
 *
 * This [Callback] implementation have error responses handling and
 * overrode [onFailure]. In [onSuccessfulResponse] you work with 100% successful response that
 * server returned, but need implement it to pass [SuccessfulReturnable] into [Continuation].
 *
 * Implementation example:
 * ```Kotlin
 *  val model = suspendCoroutine { continuation ->
 *      val call = // Getting a Call instance
 *      call.enqueue(object : MonopolyCallback(continuation) {
 *          override fun onSuccessfulResponse(
 *              call: Call<BaseResponse>, responseBody: BaseResponse
 *          ) {
 *              continuation.resume(responseBody.toModel())
 *          }
 *      })
 *  }
 * ```
 *
 * @param continuation A Kotlin Coroutines' [Continuation] to continue
 * execution with a model object.
 */
internal abstract class MonopolyCallback(
    private val continuation: Continuation<Returnable>
) : Callback<BaseResponse> {


    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
        if (response.isSuccessful) {

            val responseBody = response.body()

            if (responseBody != null) {
                onSuccessfulResponse(call, responseBody)
            } else {
                handleErrorResponse(null) // Undefined error
            }

        } else {
            val jsonResponse = response.errorBody()?.string()
            val responseBody = Gson().fromJson(jsonResponse, BaseErrorResponse::class.java)
            handleErrorResponse(responseBody)
        }
    }


    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
        Log.e("Retrofit", "Failure: ${t.message}")
        Log.e("Retrofit", "StackTrace: ${t.stackTraceToString()}")

        continuation.resume(Failure(throwable = t))
    }

    // TODO: Add KDoc
    internal abstract fun onSuccessfulResponse(call: Call<BaseResponse>, responseBody: BaseResponse)

    // TODO: Add KDoc
    protected fun handleErrorResponse(response: BaseErrorResponse?) {
        Log.e("Retrofit", "Error:\n${response.toString()}")

        if (response != null) {
            continuation.resume(response.toError())
        } else {
            continuation.resume(UndefinedError())
        }

    }

}