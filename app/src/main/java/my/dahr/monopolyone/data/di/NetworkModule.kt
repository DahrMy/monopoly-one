package my.dahr.monopolyone.data.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.dahr.monopolyone.data.network.dto.deserializer.MonopolyDeserializer
import my.dahr.monopolyone.data.network.dto.response.BaseResponse
import my.dahr.monopolyone.utils.BASE_URL
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


}