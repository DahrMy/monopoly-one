package my.dahr.monopolyone.domain.model


data class AuthorizationError(
    override val code: Int = 1,
    val description: String
) : WrongReturnable


data class ParamInvalidError(
    override val code: Int = 2,
    val description: String,
    val issues: List<Issue>
) : WrongReturnable {

    data class Issue(
        val key: String,
        val message: String
    )

}


data class InternalError(
    override val code: Int = 3,
    val description: String
) : WrongReturnable


data class AccessDenyError(
    override val code: Int = 4,
    val description: String
) : WrongReturnable


data class UndefinedRequestError(
    override val code: Int = 5,
    val description: String
) : WrongReturnable


data class ObjectsLimitError(
    override val code: Int = 6,
    val description: String
) : WrongReturnable


data class RequestsLimitError(
    override val code: Int = 7,
    val description: String
) : WrongReturnable


data class CaptchaError(
    override val code: Int = 8,
    val description: String
) : WrongReturnable


data class ServerIdleError(
    override val code: Int = 10,
    val description: String
) : WrongReturnable


data class Confirmation(
    override val code: Int = 11,
    val description: String
) : WrongReturnable


data class TwoFaCode(
    override val code: Int = 12,
    val description: String
) : WrongReturnable


data class MaintenanceError(
    override val code: Int = 98,
    val description: String
) : WrongReturnable


data class UndefinedError(
    override val code: Int = 99
) : WrongReturnable


data class UserNotExistError(
    override val code: Int = 101,
    val description: String
) : WrongReturnable


data class MuteError(
    override val code: Int = 104,
    val description: String
) : WrongReturnable


data class BlockListError(
    override val code: Int = 107,
    val description: String
) : WrongReturnable


data class AccountBlockedError(
    override val code: Int = 412,
    val description: String
) : WrongReturnable


data class InvalidTotpCodeError(
    override val code: Int = 413,
    val description: String
) : WrongReturnable


data class FrequentTotpError(
    override val code: Int = 414,
    val description: String,
    val retryAfter: Long
) : WrongReturnable


data class UnableCraftError(
    override val code: Int = 603,
    val description: String
) : WrongReturnable


data class IncompatibleCraftError(
    override val code: Int = 605,
    val description: String
) : WrongReturnable


data class ItemUsingOperationError(
    override val code: Int = 624,
    val description: String
) : WrongReturnable


data class UserPrivacySettingsError(
    override val code: Int = 701,
    val description: String
) : WrongReturnable


data class ChatRestrictionError(
    override val code: Int = 1203,
    val description: String
) : WrongReturnable