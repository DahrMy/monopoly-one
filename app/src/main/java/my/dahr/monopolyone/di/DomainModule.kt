package my.dahr.monopolyone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.domain.repository.InventoryRepository
import my.dahr.monopolyone.domain.repository.NetworkRepository
import my.dahr.monopolyone.domain.repository.SessionRepository
import my.dahr.monopolyone.domain.repository.UserRepository
import my.dahr.monopolyone.domain.usecase.friends.AddFriendUseCase
import my.dahr.monopolyone.domain.usecase.friends.DeleteFriendUseCase
import my.dahr.monopolyone.domain.usecase.friends.GetFriendsListUseCase
import my.dahr.monopolyone.domain.usecase.friends.GetListOfRequestsUseCase
import my.dahr.monopolyone.domain.usecase.inventory.GetListOfItemsUseCase
import my.dahr.monopolyone.domain.usecase.login.RefreshSessionUseCase
import my.dahr.monopolyone.domain.usecase.login.SignInUseCase
import my.dahr.monopolyone.domain.usecase.login.VerifyTotpUseCase
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase
import my.dahr.monopolyone.domain.usecase.user.FindUserUseCase

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    // USE CASES
    @Provides
    internal fun provideRequireSessionUseCase(
        sessionRepository: SessionRepository,
        networkRepository: NetworkRepository
    ): RequireSessionUseCase = RequireSessionUseCase(sessionRepository, networkRepository)

    @Provides
    internal fun provideRefreshSessionUseCase(
        sessionRepository: SessionRepository
    ): RefreshSessionUseCase = RefreshSessionUseCase(sessionRepository)

    @Provides
    internal fun provideSignInUseCase(sessionRepository: SessionRepository): SignInUseCase =
        SignInUseCase(sessionRepository)

    @Provides
    internal fun provideVerifyTotpUseCase(sessionRepository: SessionRepository): VerifyTotpUseCase =
        VerifyTotpUseCase(sessionRepository)

    @Provides
    internal fun provideAddFriendUseCase(friendsRepository: FriendsRepository): AddFriendUseCase =
        AddFriendUseCase(friendsRepository)

    @Provides
    internal fun provideDeleteFriendUseCase(friendsRepository: FriendsRepository): DeleteFriendUseCase =
        DeleteFriendUseCase(friendsRepository)

    @Provides
    internal fun provideGetFriendsListUseCase(friendsRepository: FriendsRepository): GetFriendsListUseCase =
        GetFriendsListUseCase(friendsRepository)

    @Provides
    internal fun provideGetListOfRequestsUseCase(friendsRepository: FriendsRepository): GetListOfRequestsUseCase =
        GetListOfRequestsUseCase(friendsRepository)

    @Provides
    internal fun provideGetListOfItemsUseCase(inventoryRepository: InventoryRepository): GetListOfItemsUseCase =
        GetListOfItemsUseCase(inventoryRepository)

    @Provides
    internal fun provideFindUserUseCase(userRepository: UserRepository): FindUserUseCase =
        FindUserUseCase(userRepository)

}