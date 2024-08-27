package my.dahr.monopolyone.domain.models.friends.requests

import my.dahr.monopolyone.domain.model.SuccessfulReturnable


data class FriendsRequests(
    val code: Int,
    val data: DataRequests,
): SuccessfulReturnable