package my.dahr.monopolyone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.network.api.MonopolyApi
import my.dahr.monopolyone.utils.BASE_URL
import okhttp3.Interceptor
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

        val acceptHeader = "application/json"
        val authorizationHeader = "Bearer [your API access token]"

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headersInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder() // Add your headers here
                .addHeader("Accept", acceptHeader)
                .addHeader("Authorization", authorizationHeader)
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor)
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
    fun provideSimpleApi(retrofit: Retrofit): MonopolyApi = retrofit.create(MonopolyApi::class.java)

}