package my.dahr.monopolyone.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import my.dahr.monopolyone.data.network.MonopolyCallback
import my.dahr.monopolyone.utils.SessionHelper
import my.dahr.monopolyone.utils.currentTimeInSec
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

private const val tag_logger = "SessionWorker"

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionHelper: SessionHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        Log.d(tag_logger, "SessionWorker is working")

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
                            else -> Result.retry()
                        }
                    )
                }
            }
        }

        return result

    }

    companion object {

        const val tag = "session_worker"

        fun enqueue(expiresAt: Long, appContext: Context) {
            val repeatInterval = ((expiresAt - currentTimeInSec) / 2).seconds.toJavaDuration()
            val flexTimeInterval = ((expiresAt - currentTimeInSec) / 4).seconds.toJavaDuration()

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .setRequiresBatteryNotLow(true)
                .build()

            val workRequest =
                PeriodicWorkRequestBuilder<SessionWorker>(repeatInterval, flexTimeInterval)
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
                /* uniqueWorkName = */ tag,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                workRequest
            )
        }

    }

}