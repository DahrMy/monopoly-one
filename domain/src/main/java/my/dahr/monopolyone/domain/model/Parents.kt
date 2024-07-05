package my.dahr.monopolyone.domain.model

/**
 * A model that UseCase can return.
 * Any that can be returned from an UseCase inherit from this class.
 * Main inheritors of the class are [SuccessfulReturnable] and [WrongReturnable].
 * UseCase should return a model that inherits from [SuccessfulReturnable]
 * if target operation went successfully. So all normal models often should inherit from this class.
 * But if UseCase operation went wrong it returns a model that inherits from [WrongReturnable].
 * The hierarchy consists of:
 * ```
 *                     Returnable
 *                     /        \
 *     SuccessfulReturnable     WrongReturnable
 *        /     \      \         /     \      \
 *   Model1  Model2    ...    Error1  Error2  ...
 * ```
 */
sealed interface Returnable

/**
 * The parent of a models that UseCase can return if all went successfully
 * @see Returnable
 */
internal interface SuccessfulReturnable : Returnable

/**
 * The parent of a models that UseCase can return if something went wrong
 * @see Returnable
 */
internal interface WrongReturnable : Returnable