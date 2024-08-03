package my.dahr.monopolyone.domain

import my.dahr.monopolyone.domain.model.AccessDenyError
import my.dahr.monopolyone.domain.model.AuthorizationError
import my.dahr.monopolyone.domain.model.CaptchaError
import my.dahr.monopolyone.domain.model.Confirmation
import my.dahr.monopolyone.domain.model.Failure
import my.dahr.monopolyone.domain.model.WrongReturnable
import my.dahr.monopolyone.domain.model.InternalError
import my.dahr.monopolyone.domain.model.MaintenanceError
import my.dahr.monopolyone.domain.model.MuteError
import my.dahr.monopolyone.domain.model.ObjectsLimitError
import my.dahr.monopolyone.domain.model.ParamInvalidError
import my.dahr.monopolyone.domain.model.RequestsLimitError
import my.dahr.monopolyone.domain.model.ServerIdleError
import my.dahr.monopolyone.domain.model.TwoFaCode
import my.dahr.monopolyone.domain.model.UndefinedError
import my.dahr.monopolyone.domain.model.UndefinedRequestError
import my.dahr.monopolyone.domain.model.UserNotExistError
import my.dahr.monopolyone.domain.model.BlockListError
import my.dahr.monopolyone.domain.model.AccountBlockedError
import my.dahr.monopolyone.domain.model.InvalidTotpCodeError
import my.dahr.monopolyone.domain.model.FrequentTotpError
import my.dahr.monopolyone.domain.model.UnableCraftError
import my.dahr.monopolyone.domain.model.IncompatibleCraftError
import my.dahr.monopolyone.domain.model.ItemUsingOperationError
import my.dahr.monopolyone.domain.model.UserPrivacySettingsError
import my.dahr.monopolyone.domain.model.ChatRestrictionError
import java.util.Date

val currentTimeInSec get() = (Date().time / 1000).toInt().toLong()

fun findWrongReturnable(code: Int): Class<out WrongReturnable> = when (code) {
    -1 -> Failure::class.java
    1 -> AuthorizationError::class.java
    2 -> ParamInvalidError::class.java
    3 -> InternalError::class.java
    4 -> AccessDenyError::class.java
    5 -> UndefinedRequestError::class.java
    6 -> ObjectsLimitError::class.java
    7 -> RequestsLimitError::class.java
    8 -> CaptchaError::class.java
    10 -> ServerIdleError::class.java
    11 -> Confirmation::class.java
    12 -> TwoFaCode::class.java
    98 -> MaintenanceError::class.java
    99 -> UndefinedError::class.java
    101 -> UserNotExistError::class.java
    104 -> MuteError::class.java
    107 -> BlockListError::class.java
    412 -> AccountBlockedError::class.java
    413 -> InvalidTotpCodeError::class.java
    414 -> FrequentTotpError::class.java
    603 -> UnableCraftError::class.java
    605 -> IncompatibleCraftError::class.java
    624 -> ItemUsingOperationError::class.java
    701 -> UserPrivacySettingsError::class.java
    1203 -> ChatRestrictionError::class.java
    else -> UndefinedError::class.java
}

