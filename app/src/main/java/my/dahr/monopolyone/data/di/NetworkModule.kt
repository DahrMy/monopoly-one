package my.dahr.monopolyone.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.api.FriendsApi
import my.dahr.monopolyone.data.network.api.InventoryApi
import my.dahr.monopolyone.data.network.api.UsersApi
import my.dahr.monopolyone.data.network.api.monopoly.AuthorizationApi
import my.dahr.monopolyone.data.network.dto.deserializer.MonopolyDeserializer
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
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

    private val gson get() = GsonBuilder() // TODO: Shouldn't do like here
        .registerTypeAdapter(BaseResponse::class.java, MonopolyDeserializer())
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi = retrofit.create(
        AuthorizationApi::class.java)

    @Provides
    @Singleton
    fun provideFriendsApi(retrofit: Retrofit): FriendsApi = retrofit.create(FriendsApi::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideInventoryApi(retrofit: Retrofit): InventoryApi = retrofit.create(InventoryApi::class.java)

}