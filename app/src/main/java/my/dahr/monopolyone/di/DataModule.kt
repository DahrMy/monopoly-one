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
import my.dahr.monopolyone.data.network.buildMonopolyRetrofit
import my.dahr.monopolyone.data.network.buildPlainRetrofit
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.repository.NetworkRepositoryImpl
import my.dahr.monopolyone.data.repository.SessionRepositoryImpl
import my.dahr.monopolyone.data.source.auth.local.SessionLocalDataSource
import my.dahr.monopolyone.data.source.auth.local.SessionSharedPrefDataSourceImpl
import my.dahr.monopolyone.data.source.auth.remote.SessionRemoteDataSource
import my.dahr.monopolyone.data.source.auth.remote.SessionRetrofitDataSourceImpl
import my.dahr.monopolyone.data.source.auth.remote.deserializer.AuthResponseDeserializer
import my.dahr.monopolyone.data.source.ip.local.IpLocalDataSource
import my.dahr.monopolyone.data.source.ip.local.IpSharedPrefDataSourceImpl
import my.dahr.monopolyone.data.source.ip.remote.IpRemoteDataSource
import my.dahr.monopolyone.data.source.ip.remote.IpRetrofitDataSourceImpl
import my.dahr.monopolyone.domain.repository.NetworkRepository
import my.dahr.monopolyone.domain.repository.SessionRepository

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


    // APIs

    @Provides
    internal fun provideIpApi(): IpApi = buildPlainRetrofit().create(IpApi::class.java)

    @Provides
    internal fun provideAuthorizationApi(): AuthorizationApi =
        buildMonopolyRetrofit(BaseResponse::class.java, AuthResponseDeserializer())
            .create(AuthorizationApi::class.java)


    // OTHER

    @Provides
    internal fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

}
