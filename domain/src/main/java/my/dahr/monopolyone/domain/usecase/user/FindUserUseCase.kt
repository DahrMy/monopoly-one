package my.dahr.monopolyone.domain.usecase.user

import my.dahr.monopolyone.domain.model.user.params.UserParams
import my.dahr.monopolyone.domain.repository.UserRepository

class FindUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userParams: UserParams){
        userRepository.getUsersList(userParams)
    }
}