package my.dahr.monopolyone.domain.usecase.friends

import my.dahr.monopolyone.domain.model.friends.params.AddParams
import my.dahr.monopolyone.domain.repository.FriendsRepository

class AddFriendUseCase(private val friendsRepository: FriendsRepository) {

    suspend operator fun invoke(addParams: AddParams){
        friendsRepository.addFriend(addParams)
    }
}