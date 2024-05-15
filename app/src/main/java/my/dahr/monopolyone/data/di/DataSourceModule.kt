package my.dahr.monopolyone.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.datasource.FriendsDataSourceImpl
import my.dahr.monopolyone.data.network.datasource.UsersDataSourceImpl
import my.dahr.monopolyone.domain.datasource.FriendsDataSource
import my.dahr.monopolyone.domain.datasource.UsersDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindFriendsDataSource(impl: FriendsDataSourceImpl): FriendsDataSource

    @Binds
    abstract fun bindUsersDataSource(impl: UsersDataSourceImpl): UsersDataSource
}