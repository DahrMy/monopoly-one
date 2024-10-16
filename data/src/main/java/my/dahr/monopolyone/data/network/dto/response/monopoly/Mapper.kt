package my.dahr.monopolyone.data.network.dto.response.monopoly

import my.dahr.monopolyone.data.network.dto.response.monopoly.error.BaseErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.DefaultErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.FrequentTotpErrorResponse
import my.dahr.monopolyone.data.network.dto.response.monopoly.error.ParamInvalidErrorResponse
import my.dahr.monopolyone.domain.model.AccessDenyError
import my.dahr.monopolyone.domain.model.AccountBlockedError
import my.dahr.monopolyone.domain.model.AuthorizationError
import my.dahr.monopolyone.domain.model.BlockListError
import my.dahr.monopolyone.domain.model.CaptchaError
import my.dahr.monopolyone.domain.model.ChatRestrictionError
import my.dahr.monopolyone.domain.model.Confirmation
import my.dahr.monopolyone.domain.model.FrequentTotpError
import my.dahr.monopolyone.domain.model.IncompatibleCraftError
import my.dahr.monopolyone.domain.model.InternalError
import my.dahr.monopolyone.domain.model.InvalidTotpCodeError
import my.dahr.monopolyone.domain.model.ItemUsingOperationError
import my.dahr.monopolyone.domain.model.MaintenanceError
import my.dahr.monopolyone.domain.model.MuteError
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

internal fun BaseErrorResponse.toError() = when (this) {
    is DefaultErrorResponse -> this.toError()
    is ParamInvalidErrorResponse -> this.toError()
    is FrequentTotpErrorResponse -> this.toError()
    else -> UndefinedError()
}


internal fun DefaultErrorResponse.toError() = when (code) {
    1 -> AuthorizationError(description = description)
    3 -> InternalError(description = description)
    4 -> AccessDenyError(description = description)
    5 -> UndefinedRequestError(description = description)
    6 -> ObjectsLimitError(description = description)
    7 -> RequestsLimitError(description = description)
    8 -> CaptchaError(description = description)
    10 -> ServerIdleError(description = description)
    11 -> Confirmation(description = description)
    12 -> TwoFaCode(description = description)
    98 -> MaintenanceError(description = description)
    99 -> UndefinedError()
    101 -> UserNotExistError(description = description)
    104 -> MuteError(description = description)
    107 -> BlockListError(description = description)
    412 -> AccountBlockedError(description = description)
    413 -> InvalidTotpCodeError(description = description)
    603 -> UnableCraftError(description = description)
    605 -> IncompatibleCraftError(description = description)
    624 -> ItemUsingOperationError(description = description)
    701 -> UserPrivacySettingsError(description = description)
    1203 -> ChatRestrictionError(description = description)
    else -> UndefinedError()
}


internal fun ParamInvalidErrorResponse.toError() = ParamInvalidError(
    description = description,
    issues = data.issues.toModelList()
)

internal fun List<ParamInvalidErrorResponse.Data.Issue>.toModelList(): List<ParamInvalidError.Issue> {
    val result = mutableListOf<ParamInvalidError.Issue>()

    this.map { item ->
        result.add(item.toModel())
    }

    return result
}

internal fun ParamInvalidErrorResponse.Data.Issue.toModel() = ParamInvalidError.Issue(
    path = path,
    message = message
)


internal fun FrequentTotpErrorResponse.toError() = FrequentTotpError(
    description = description,
    retryAfter = data.retryAfter
)