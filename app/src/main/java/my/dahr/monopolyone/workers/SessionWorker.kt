package my.dahr.monopolyone.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.data.network.dto.response.SessionResponse
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.currentTimeInSec
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

private const val tag = "SessionWorker"

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionHelper: SessionHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        Log.d(tag, "SessionWorker is working")

        val requestStatusFlow = MutableSharedFlow<RequestStatus>()

        CoroutineScope(Dispatchers.IO + Job()).launch {
            sessionHelper.session?.let {
                sessionHelper.refreshSession(it.accessToken) {
                    object : MonopolyCallback<SessionResponse>(null, requestStatusFlow) {}
                }
            }
        }

        val result = suspendCancellableCoroutine { continuation ->
            CoroutineScope(Dispatchers.Default).launch {
                requestStatusFlow.collect { status ->
                    continuation.resume(
                        /* return */ when (status) {
                            RequestStatus.Success -> Result.success()
                            else -> Result.failure()
                        }
                    )
                }
            }
        }

        return result

    }

    companion object {

        fun enqueue(expiresAt: Long, @ApplicationContext appContext: Context) {
            val repeatInterval = ((expiresAt - currentTimeInSec) / 2).seconds.toJavaDuration()
            val flexTimeInterval = ((expiresAt - currentTimeInSec) / 4).seconds.toJavaDuration()

            TODO("Not yet implemented")

            val workRequest = PeriodicWorkRequestBuilder<SessionWorker>(
                repeatInterval, flexTimeInterval
            )
                .build()


            WorkManager.getInstance(appContext)
//                .updateWork()
                .enqueue(workRequest)

        }

    }

}