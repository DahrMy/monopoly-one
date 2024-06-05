package my.dahr.monopolyone.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.data.repository.FriendsRepositoryImpl
import my.dahr.monopolyone.data.repository.InventoryRepositoryImpl
import my.dahr.monopolyone.data.repository.UsersRepositoryImpl
import my.dahr.monopolyone.domain.repository.InventoryRepository
import my.dahr.monopolyone.domain.repository.UsersRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFriendsRepository(impl: FriendsRepositoryImpl): FriendsRepository

    @Binds
    abstract fun bindUsersRepository(impl: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun bindInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository
}