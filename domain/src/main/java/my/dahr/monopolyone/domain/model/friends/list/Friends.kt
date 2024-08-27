package my.dahr.monopolyone.domain.models.friends.list

import my.dahr.monopolyone.domain.model.SuccessfulReturnable

data class Friends(
    val code: Int,
    val data: Data
): SuccessfulReturnable