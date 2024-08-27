package my.dahr.monopolyone.domain.usecase.friends

import my.dahr.monopolyone.domain.model.friends.params.ListParams
import my.dahr.monopolyone.domain.repository.FriendsRepository

class GetFriendsListUseCase(private val repository: FriendsRepository) {
    suspend operator fun invoke(listParams: ListParams){
        repository.getFriendsList(listParams)
    }
}