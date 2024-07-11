package my.dahr.monopolyone.data.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
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

internal fun buildMonopolyRetrofit(
    baseDtoClazz: Class<BaseResponse>,
    deserializer: JsonDeserializer<BaseResponse>
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

internal fun buildPlainRetrofit() = Retrofit.Builder()
        .baseUrl(MONOPOLY_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()