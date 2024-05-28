package my.dahr.monopolyone.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import my.dahr.monopolyone.R
import javax.inject.Inject

class ResourceRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun getBitmapFromDrawableRes(@DrawableRes id: Int): Bitmap {

        val drawable = AppCompatResources.getDrawable(context, id)
        if (drawable != null) {
            return drawable.toBitmap()
        } else {
            val reserveDrawable =
                AppCompatResources.getDrawable(context, R.drawable.ic_disabled_by_default)
            return reserveDrawable!!.toBitmap()
        }

    }

    fun getErrorMessageStringResource(code: Int) = when (code) {
        1 -> context.resources.getString(R.string.dialog_error_1_text)
        2 -> context.resources.getString(R.string.dialog_error_2_text)
        3 -> context.resources.getString(R.string.dialog_error_3_text)
        4 -> context.resources.getString(R.string.dialog_error_4_text)
        5 -> context.resources.getString(R.string.dialog_error_5_text)
        6 -> context.resources.getString(R.string.dialog_error_6_text)
        7 -> context.resources.getString(R.string.dialog_error_7_text)
        8 -> context.resources.getString(R.string.dialog_error_8_text)
        10 -> context.resources.getString(R.string.dialog_error_10_text)
        11 -> context.resources.getString(R.string.dialog_error_11_text)
        12 -> context.resources.getString(R.string.dialog_error_12_text)
        98 -> context.resources.getString(R.string.dialog_error_98_text)
        else -> { context.resources.getString(R.string.dialog_error_99_text) }
    }

}