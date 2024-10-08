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
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.Returnable
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.session.Session
import my.dahr.monopolyone.domain.usecase.login.RefreshSessionUseCase
import my.dahr.monopolyone.utils.currentTimeInSec
import my.dahr.monopolyone.utils.printErrorln
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration


private const val TAG_LOGGER = "SessionWorker"


@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val refreshSessionUseCase: RefreshSessionUseCase
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        Log.d(TAG_LOGGER, "SessionWorker is working")
        val resultInstance = refreshSessionUseCase()
        return makeResult(resultInstance)
    }


    private fun makeResult(resultInstance: Returnable?) = when (resultInstance) {

        is Session -> {
            Result.success()
        }

        is Failure -> {
            resultInstance.throwable.printStackTrace()
            Result.failure()
        }

        null -> {
            printErrorln("Session instance is null")
            Result.failure()
        }

        else -> {
            if (resultInstance is WrongReturnable) {
                printErrorln("${resultInstance.code}: ${resultInstance.description}")
            } else {
                printErrorln("Undefined error is occurred")
            }
            Result.failure()
        }

    }


    companion object {

        const val TAG = "session_worker"

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
                /* uniqueWorkName = */ TAG,
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                workRequest
            )
        }

    }

}