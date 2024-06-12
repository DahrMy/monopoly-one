package my.dahr.monopolyone.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import my.dahr.monopolyone.utils.SessionHelper

@HiltWorker
class SessionWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionHelper: SessionHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        Log.d("SessionWorker", "SessionWorker is working")

        return Result.success()
    }

}