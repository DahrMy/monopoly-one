package my.dahr.monopolyone.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.api.AuthorizationApi
import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi = retrofit.create(AuthorizationApi::class.java)

    @Provides
    @Singleton
    fun provideFriendsApi(retrofit: Retrofit): FriendsApi = retrofit.create(FriendsApi::class.java)

}