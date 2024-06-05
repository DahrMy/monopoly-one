package my.dahr.monopolyone.data.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import my.dahr.monopolyone.R
import my.dahr.monopolyone.data.models.RequestStatus.*
import my.dahr.monopolyone.data.models.RequestStatus
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

    fun getErrorMessageStringResource(status: RequestStatus) = when (status) {
        AuthorizationError -> context.resources.getString(R.string.dialog_error_1_text)
        ParamInvalidError -> context.resources.getString(R.string.dialog_error_2_text) // TODO: Use list from `data`
        InternalError -> context.resources.getString(R.string.dialog_error_3_text)
        AccessDenyError -> context.resources.getString(R.string.dialog_error_4_text)
        UndefinedRequestError -> context.resources.getString(R.string.dialog_error_5_text)
        ObjectsLimitError -> context.resources.getString(R.string.dialog_error_6_text)
        RequestsLimitError -> context.resources.getString(R.string.dialog_error_7_text)
        CaptchaError -> context.resources.getString(R.string.dialog_error_8_text)
        ServerIdleError -> context.resources.getString(R.string.dialog_error_10_text)
        Confirmation -> context.resources.getString(R.string.dialog_error_11_text)
        TwoFaCode -> context.resources.getString(R.string.dialog_error_12_text)
        MaintenanceError -> context.resources.getString(R.string.dialog_error_98_text)
        UserNotExistError -> context.resources.getString(R.string.dialog_error_101_text)
        MuteError -> context.resources.getString(R.string.dialog_error_104_text)
        BlockListError -> context.resources.getString(R.string.dialog_error_107_text)
        AccountBlockedError -> context.resources.getString(R.string.dialog_error_412_text)
        InvalidTotpCodeError -> context.resources.getString(R.string.dialog_error_413_text)
        FrequentTotpError -> context.resources.getString(R.string.dialog_error_414_text) // TODO: Use time from `data`
        UnableCraftError -> context.resources.getString(R.string.dialog_error_603_text)
        IncompatibleCraftError -> context.resources.getString(R.string.dialog_error_605_text)
        ItemUsingOperationError -> context.resources.getString(R.string.dialog_error_624_text)
        UserPrivacySettingsError -> context.resources.getString(R.string.dialog_error_701_text)
        ChatRestrictionError -> context.resources.getString(R.string.dialog_error_1203_text)
        else /* UndefinedError */ -> context.resources.getString(R.string.dialog_error_99_text)
    }

}