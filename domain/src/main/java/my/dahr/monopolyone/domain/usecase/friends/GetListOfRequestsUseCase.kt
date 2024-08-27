package my.dahr.monopolyone.domain.usecase.friends

import my.dahr.monopolyone.domain.model.friends.params.RequestsParams
import my.dahr.monopolyone.domain.repository.FriendsRepository

class GetListOfRequestsUseCase (private val repository: FriendsRepository){
    suspend operator fun invoke(requestsParams: RequestsParams){
        repository.getFriendsRequestsList(requestsParams)
    }
}