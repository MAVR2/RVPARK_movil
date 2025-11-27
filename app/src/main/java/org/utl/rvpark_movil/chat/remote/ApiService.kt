package org.utl.rvpark_movil.chat.remote

import org.utl.rvpark_movil.chat.model.GeminiRequest
import org.utl.rvpark_movil.chat.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiServiceApi {
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

