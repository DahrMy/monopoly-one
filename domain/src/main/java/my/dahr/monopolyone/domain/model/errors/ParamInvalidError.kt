package my.dahr.monopolyone.domain.model.errors

import my.dahr.monopolyone.domain.model.WrongReturnable

data class ParamInvalidError(
    override val code: Int = 2,
    val issues: List<Issue>
) : WrongReturnable

data class Issue(
    val key: String,
    val message: String
)