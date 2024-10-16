package my.dahr.monopolyone.data.network

import my.dahr.monopolyone.data.MONOPOLY_BASE_URL
import my.dahr.monopolyone.data.network.dto.response.monopoly.BaseResponse
import my.dahr.monopolyone.data.utils.buildMonopolyGson
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
): Retrofit = Retrofit.Builder()
    .baseUrl(MONOPOLY_BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(
        buildMonopolyGson(baseDtoClazz, deserializer)
    ))
    .build()

fun buildPlainRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(MONOPOLY_BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()