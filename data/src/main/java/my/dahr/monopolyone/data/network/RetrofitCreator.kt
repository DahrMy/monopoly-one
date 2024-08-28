package my.dahr.monopolyone.data.network

import com.google.gson.GsonBuilder
import my.dahr.monopolyone.data.MONOPOLY_BASE_URL
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val logger
    get() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

private val okHttpClient
    get() = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

// TODO: Add KDoc
fun buildMonopolyRetrofit(
    baseDtoClazz: Class<out BaseResponse>,
    deserializer: MonopolyResponseDeserializer
): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(baseDtoClazz, deserializer)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    return Retrofit.Builder()
        .baseUrl(MONOPOLY_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun buildPlainRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(MONOPOLY_BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()