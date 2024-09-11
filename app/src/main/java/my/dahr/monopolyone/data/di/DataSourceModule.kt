package my.dahr.monopolyone.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.datasource.InventoryDataSourceImpl
import my.dahr.monopolyone.data.network.datasource.UsersDataSourceImpl
import my.dahr.monopolyone.domain.datasource.InventoryDataSource
import my.dahr.monopolyone.domain.datasource.UsersDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {


    @Binds
    abstract fun bindUsersDataSource(impl: UsersDataSourceImpl): UsersDataSource

    @Binds
    abstract fun bindInventoryDataSource(impl: InventoryDataSourceImpl): InventoryDataSource
}