package my.dahr.monopolyone.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import my.dahr.monopolyone.data.models.RequestStatus
import my.dahr.monopolyone.utils.SessionHelper

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionHelper: SessionHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        Log.d("SessionWorker", "SessionWorker is working")

        val flow = MutableSharedFlow<RequestStatus>()

        sessionHelper.safeUse(flow = flow) {

        }

        return Result.success()
    }

}