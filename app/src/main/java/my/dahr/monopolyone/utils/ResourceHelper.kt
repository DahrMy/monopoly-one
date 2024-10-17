package my.dahr.monopolyone.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import my.dahr.monopolyone.R
import my.dahr.monopolyone.domain.model.AccessDenyError
import my.dahr.monopolyone.domain.model.AccountBlockedError
import my.dahr.monopolyone.domain.model.AuthorizationError
import my.dahr.monopolyone.domain.model.BlockListError
import my.dahr.monopolyone.domain.model.CaptchaError
import my.dahr.monopolyone.domain.model.ChatRestrictionError
import my.dahr.monopolyone.domain.model.Confirmation
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.FrequentTotpError
import my.dahr.monopolyone.domain.model.IncompatibleCraftError
import my.dahr.monopolyone.domain.model.InternalError
import my.dahr.monopolyone.domain.model.InvalidTotpCodeError
import my.dahr.monopolyone.domain.model.ItemUsingOperationError
import my.dahr.monopolyone.domain.model.MaintenanceError
import my.dahr.monopolyone.domain.model.MuteError
import my.dahr.monopolyone.domain.model.NoInternetConnectionError
import my.dahr.monopolyone.domain.model.ObjectsLimitError
import my.dahr.monopolyone.domain.model.ParamInvalidError
import my.dahr.monopolyone.domain.model.RequestsLimitError
import my.dahr.monopolyone.domain.model.ServerIdleError
import my.dahr.monopolyone.domain.model.TwoFaCode
import my.dahr.monopolyone.domain.model.UnableCraftError
import my.dahr.monopolyone.domain.model.UndefinedError
import my.dahr.monopolyone.domain.model.UndefinedRequestError
import my.dahr.monopolyone.domain.model.UserNotExistError
import my.dahr.monopolyone.domain.model.UserPrivacySettingsError
import my.dahr.monopolyone.domain.model.WrongReturnable


internal fun Context.getBitmapById(@DrawableRes id: Int): Bitmap {

    val drawable = AppCompatResources.getDrawable(this, id)
    if (drawable != null) {
        return drawable.toBitmap()
    } else {
        val reserveDrawable =
            AppCompatResources.getDrawable(this, R.drawable.ic_disabled_by_default)
        return reserveDrawable!!.toBitmap()
    }

}


internal fun Context.getErrorMessageStringResource(data: WrongReturnable) = when (data) {
    is Failure /* -1 */ -> resources.getString(R.string.dialog_failure_text)
    is NoInternetConnectionError /* -1 */ -> resources.getString(R.string.dialog_noInternet_text)
    is AuthorizationError /* 1 */ -> resources.getString(R.string.dialog_error_1_text)
    is ParamInvalidError /* 2 */ -> resources.getString(R.string.dialog_error_2_text)
    is InternalError /* 3 */ -> resources.getString(R.string.dialog_error_3_text)
    is AccessDenyError /* 4 */ -> resources.getString(R.string.dialog_error_4_text)
    is UndefinedRequestError /* 5 */ -> resources.getString(R.string.dialog_error_5_text)
    is ObjectsLimitError /* 6 */ -> resources.getString(R.string.dialog_error_6_text)
    is RequestsLimitError /* 7 */ -> resources.getString(R.string.dialog_error_7_text)
    is CaptchaError /* 8 */ -> resources.getString(R.string.dialog_error_8_text)
    is ServerIdleError /* 10 */ -> resources.getString(R.string.dialog_error_10_text)
    is Confirmation /* 11 */ -> resources.getString(R.string.dialog_error_11_text)
    is TwoFaCode /* 12 */ -> resources.getString(R.string.dialog_error_12_text)
    is MaintenanceError /* 98 */ -> resources.getString(R.string.dialog_error_98_text)
    is UndefinedError /* 99 */ -> resources.getString(R.string.dialog_error_99_text)
    is UserNotExistError /* 101 */ -> resources.getString(R.string.dialog_error_101_text)
    is MuteError /* 104 */ -> resources.getString(R.string.dialog_error_104_text)
    is BlockListError /* 107 */ -> resources.getString(R.string.dialog_error_107_text)
    is AccountBlockedError /* 412 */ -> resources.getString(R.string.dialog_error_412_text)
    is InvalidTotpCodeError /* 413 */ -> resources.getString(R.string.dialog_error_413_text)
    is FrequentTotpError /* 414 */ -> {
        String.format(resources.getString(R.string.dialog_error_414_text), data.retryAfter)
    }
    is UnableCraftError /* 603 */ -> resources.getString(R.string.dialog_error_603_text)
    is IncompatibleCraftError /* 605 */ -> resources.getString(R.string.dialog_error_605_text)
    is ItemUsingOperationError /* 624 */ -> resources.getString(R.string.dialog_error_624_text)
    is UserPrivacySettingsError /* 701 */ -> resources.getString(R.string.dialog_error_701_text)
    is ChatRestrictionError /* 1203 */ -> resources.getString(R.string.dialog_error_1203_text)
}