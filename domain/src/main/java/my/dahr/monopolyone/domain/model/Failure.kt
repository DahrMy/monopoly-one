package my.dahr.monopolyone.domain.model

/**
 * This object needs in situation where is throwing exceptions.
 * It contains [Throwable] object to provide full information about appeared failure.
 * Usually, this object generates using local information at a platform, not from a server response.
 */
data class Failure(
    override val code: Int = -1,
    override val description: String = "",
    val throwable: Throwable,
) : WrongReturnable