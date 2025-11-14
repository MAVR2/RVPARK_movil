package org.utl.rvpark_movil.login.data.repository

import org.utl.rvpark_movil.login.data.model.LoginRequest
import org.utl.rvpark_movil.login.data.model.LoginResponse
import org.utl.rvpark_movil.login.data.remote.ApiService
import org.utl.rvpark_movil.utils.RetrofitClient

class LoginRepository{

    private val api = RetrofitClient.createService(ApiService::class.java)

    suspend fun Login(user: LoginRequest): LoginResponse{
        return  api.login(user)
    }
}