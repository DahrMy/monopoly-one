package my.dahr.monopolyone.utils

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton
import com.github.leandroborgesferreira.loadingbutton.customViews.ProgressButton
import kotlinx.coroutines.delay

internal suspend fun ProgressButton.endAnimation(
    @ColorInt color: Int,
    bitmap: Bitmap,
    timeMillis: Long
) {
    this.doneLoadingAnimation(color, bitmap)
    delay(timeMillis)
    this.revertAnimation()
}

internal suspend fun ProgressButton.endAnimation(@ColorInt color: Int, bitmap: Bitmap) {
    endAnimation(color, bitmap, 1000)
}

internal suspend fun CircularProgressButton.endAnimation(bitmap: Bitmap) {
    endAnimation(solidColor, bitmap, 1000)
}