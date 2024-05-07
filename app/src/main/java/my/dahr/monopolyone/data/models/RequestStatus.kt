package my.dahr.monopolyone.data.models

enum class RequestStatus(code: Int) {
    Loading(-1),
    Success(0),
    Confirmation(11),
    TwoFaCode(12),
    AuthorizationError(1),
    ParamInvalidError(2),
    InternalError(3),
    AccessDenyError(4),
    UndefinedRequestError(5),
    ObjectsLimitError(6),
    RequestsLimitError(7),
    CaptchaError(8),
    ServerIdleError(10),
    MaintenanceError(98),
    UndefinedError(99),
    Failure(-1),
}