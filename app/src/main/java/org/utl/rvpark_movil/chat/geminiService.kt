package org.utl.rvpark_movil.chat

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.utl.rvpark_movil.chat.remote.GeminiServiceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeminiService {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    val api: GeminiServiceApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GeminiServiceApi::class.java)
}
