package my.dahr.monopolyone.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.data.network.api.InventoryApi
import my.dahr.monopolyone.data.network.api.IpApi
import my.dahr.monopolyone.data.network.api.UsersApi
import my.dahr.monopolyone.utils.BASE_URL
import my.dahr.monopolyone.utils.MY_IP_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val logger get() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient get() = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi = retrofit.create(AuthorizationApi::class.java)

    @Provides
    @Singleton
    fun provideFriendsApi(retrofit: Retrofit): FriendsApi = retrofit.create(FriendsApi::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideInventoryApi(retrofit: Retrofit): InventoryApi = retrofit.create(InventoryApi::class.java)

    @Provides
    @Singleton
    fun provideIpApi(): IpApi {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(MY_IP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(IpApi::class.java)
    }

}