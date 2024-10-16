package my.dahr.monopolyone.domain.model


data class AuthorizationError(
    override val code: Int = 1,
    override val description: String
) : WrongReturnable


data class ParamInvalidError(
    override val code: Int = 2,
    override val description: String,
    val issues: List<Issue>
) : WrongReturnable {

    data class Issue(
        val path: String,
        val message: String
    )

}


data class InternalError(
    override val code: Int = 3,
    override val description: String
) : WrongReturnable


data class AccessDenyError(
    override val code: Int = 4,
    override val description: String
) : WrongReturnable


data class UndefinedRequestError(
    override val code: Int = 5,
    override val description: String
) : WrongReturnable


data class ObjectsLimitError(
    override val code: Int = 6,
    override val description: String
) : WrongReturnable


data class RequestsLimitError(
    override val code: Int = 7,
    override val description: String
) : WrongReturnable


data class CaptchaError(
    override val code: Int = 8,
    override val description: String
) : WrongReturnable


data class ServerIdleError(
    override val code: Int = 10,
    override val description: String
) : WrongReturnable


data class Confirmation(
    override val code: Int = 11,
    override val description: String
) : WrongReturnable


data class TwoFaCode(
    override val code: Int = 12,
    override val description: String
) : WrongReturnable


data class MaintenanceError(
    override val code: Int = 98,
    override val description: String
) : WrongReturnable


data class UndefinedError(
    override val code: Int = 99,
    override val description: String = ""
) : WrongReturnable


data class UserNotExistError(
    override val code: Int = 101,
    override val description: String
) : WrongReturnable


data class MuteError(
    override val code: Int = 104,
    override val description: String
) : WrongReturnable


data class BlockListError(
    override val code: Int = 107,
    override val description: String
) : WrongReturnable


data class AccountBlockedError(
    override val code: Int = 412,
    override val description: String
) : WrongReturnable


data class InvalidTotpCodeError(
    override val code: Int = 413,
    override val description: String
) : WrongReturnable


data class FrequentTotpError(
    override val code: Int = 414,
    override val description: String,
    val retryAfter: Long
) : WrongReturnable


data class UnableCraftError(
    override val code: Int = 603,
    override val description: String
) : WrongReturnable


data class IncompatibleCraftError(
    override val code: Int = 605,
    override val description: String
) : WrongReturnable


data class ItemUsingOperationError(
    override val code: Int = 624,
    override val description: String
) : WrongReturnable


data class UserPrivacySettingsError(
    override val code: Int = 701,
    override val description: String
) : WrongReturnable


data class ChatRestrictionError(
    override val code: Int = 1203,
    override val description: String
) : WrongReturnable