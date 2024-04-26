package my.dahr.monopolyone.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.data.repository.FriendsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
   abstract fun bindFriendsRepository(impl: FriendsRepositoryImpl): FriendsRepository

}