package org.utl.rvpark_movil.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    fun <T> createService(serviceClass: Class<T>, tokenProvider: () -> String? = { null }): T {

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenProvider))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}


class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider()

        val req = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            req.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(req.build())
    }
}
