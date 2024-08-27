package my.dahr.monopolyone.domain.model.user.info

import my.dahr.monopolyone.domain.model.SuccessfulReturnable


data class Users(
    val code: Int,
    val data: List<Data>,
) : SuccessfulReturnable