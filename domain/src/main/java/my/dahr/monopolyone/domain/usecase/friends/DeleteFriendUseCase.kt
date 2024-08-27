package my.dahr.monopolyone.domain.usecase.friends

import my.dahr.monopolyone.domain.model.friends.params.DeleteParams
import my.dahr.monopolyone.domain.repository.FriendsRepository

class DeleteFriendUseCase(private val friendsRepository: FriendsRepository) {
    suspend operator fun invoke(deleteParams: DeleteParams) {
        friendsRepository.deleteFriend(deleteParams)
    }
}