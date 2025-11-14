package org.utl.rvpark_movil.login.data.remote

import org.utl.rvpark_movil.login.data.model.LoginRequest
import org.utl.rvpark_movil.login.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("/api/auth/login")
    suspend fun login(@Body user: LoginRequest): LoginResponse
}
