package my.dahr.monopolyone.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.SHARED_PREFERENCES
import my.dahr.monopolyone.data.network.api.IpApi
import my.dahr.monopolyone.data.network.api.monopoly.AuthorizationApi
import my.dahr.monopolyone.data.network.api.monopoly.FriendsApi
import my.dahr.monopolyone.data.network.api.monopoly.InventoryApi
import my.dahr.monopolyone.data.network.api.monopoly.UsersApi
import my.dahr.monopolyone.data.network.buildMonopolyRetrofit
import my.dahr.monopolyone.data.network.buildPlainRetrofit
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.repository.FriendsRepositoryImpl
import my.dahr.monopolyone.data.repository.InventoryRepositoryImpl
import my.dahr.monopolyone.data.repository.NetworkRepositoryImpl
import my.dahr.monopolyone.data.repository.SessionRepositoryImpl
import my.dahr.monopolyone.data.repository.UserRepositoryImpl
import my.dahr.monopolyone.data.source.auth.local.SessionLocalDataSource
import my.dahr.monopolyone.data.source.auth.local.SessionSharedPrefDataSourceImpl
import my.dahr.monopolyone.data.source.auth.remote.SessionRemoteDataSource
import my.dahr.monopolyone.data.source.auth.remote.SessionRetrofitDataSourceImpl
import my.dahr.monopolyone.data.source.auth.remote.deserializer.AuthResponseDeserializer
import my.dahr.monopolyone.data.source.friends.remote.FriendsDataSource
import my.dahr.monopolyone.data.source.friends.remote.FriendsDataSourceImpl
import my.dahr.monopolyone.data.source.friends.remote.deserializer.FriendsResponseDeserializer
import my.dahr.monopolyone.data.source.inventory.remote.InventoryDataSource
import my.dahr.monopolyone.data.source.inventory.remote.InventoryDataSourceImpl
import my.dahr.monopolyone.data.source.inventory.remote.deserializer.InventoryResponseDeserializer
import my.dahr.monopolyone.data.source.ip.local.IpLocalDataSource
import my.dahr.monopolyone.data.source.ip.local.IpSharedPrefDataSourceImpl
import my.dahr.monopolyone.data.source.ip.remote.IpRemoteDataSource
import my.dahr.monopolyone.data.source.ip.remote.IpRetrofitDataSourceImpl
import my.dahr.monopolyone.data.source.user.remote.UserDataSource
import my.dahr.monopolyone.data.source.user.remote.UserDataSourceImpl
import my.dahr.monopolyone.data.source.user.remote.deserializer.UserResponseDeserializer
import my.dahr.monopolyone.domain.repository.FriendsRepository
import my.dahr.monopolyone.domain.repository.InventoryRepository
import my.dahr.monopolyone.domain.repository.NetworkRepository
import my.dahr.monopolyone.domain.repository.SessionRepository
import my.dahr.monopolyone.domain.repository.UserRepository
import my.dahr.monopolyone.domain.usecase.login.SignInUseCase
import my.dahr.monopolyone.domain.usecase.login.VerifyTotpUseCase
import my.dahr.monopolyone.domain.usecase.session.RequireSessionUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    // REPOSITORIES

    @Provides
    internal fun provideNetworkRepository(
        ipRemoteDataSource: IpRemoteDataSource,
        ipLocalDataSource: IpLocalDataSource
    ): NetworkRepository = NetworkRepositoryImpl(ipRemoteDataSource, ipLocalDataSource)

    @Provides
    internal fun provideSessionRepository(
        sessionRemoteDataSource: SessionRemoteDataSource,
        sessionLocalDataSource: SessionLocalDataSource
    ): SessionRepository = SessionRepositoryImpl(sessionRemoteDataSource, sessionLocalDataSource)

    @Provides
    internal fun provideFriendsRepository(
        friendsDataSource: FriendsDataSource,
    ): FriendsRepository = FriendsRepositoryImpl(friendsDataSource)

    @Provides
    internal fun provideInventoryRepository(
        inventoryDataSource: InventoryDataSource
    ): InventoryRepository = InventoryRepositoryImpl(inventoryDataSource)

    @Provides
    internal fun provideUserRepository(
        userDataSource: UserDataSource
    ): UserRepository = UserRepositoryImpl(userDataSource)


    // DATA SOURCES

    @Provides
    internal fun provideIpRemoteDataSource(api: IpApi): IpRemoteDataSource =
        IpRetrofitDataSourceImpl(api)

    @Provides
    internal fun provideIpLocalDataSource(sharedPref: SharedPreferences): IpLocalDataSource =
        IpSharedPrefDataSourceImpl(sharedPref)

    @Provides
    internal fun provideSessionRemoteDataSource(api: AuthorizationApi): SessionRemoteDataSource =
        SessionRetrofitDataSourceImpl(api)

    @Provides
    internal fun provideSessionLocalDataSource(
        sharedPref: SharedPreferences
    ): SessionLocalDataSource = SessionSharedPrefDataSourceImpl(sharedPref)

    @Provides
    internal fun provideFriendsDataSource(api: FriendsApi): FriendsDataSource =
        FriendsDataSourceImpl(api)

    @Provides
    internal fun provideInventoryDataSource(api: InventoryApi): InventoryDataSource =
        InventoryDataSourceImpl(api)

    @Provides
    internal fun provideUserDataSource(api: UsersApi): UserDataSource =
        UserDataSourceImpl(api)


    // APIs
    @Provides
    internal fun provideIpApi(): IpApi = buildPlainRetrofit().create(IpApi::class.java)

    @Provides
    internal fun provideFriendsApi(): FriendsApi =
        buildMonopolyRetrofit(BaseResponse::class.java, FriendsResponseDeserializer())
            .create(FriendsApi::class.java)

    @Provides
    internal fun provideInventoryApi(): InventoryApi =
        buildMonopolyRetrofit(BaseResponse::class.java, InventoryResponseDeserializer())
            .create(InventoryApi::class.java)

    @Provides
    internal fun provideUsersApi(): UsersApi =
        buildMonopolyRetrofit(BaseResponse::class.java, UserResponseDeserializer())
            .create(UsersApi::class.java)

    @Provides
    internal fun provideAuthorizationApi(): AuthorizationApi =
        buildMonopolyRetrofit(BaseResponse::class.java, AuthResponseDeserializer())
            .create(AuthorizationApi::class.java)

    // OTHER

    @Provides
    internal fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

}
