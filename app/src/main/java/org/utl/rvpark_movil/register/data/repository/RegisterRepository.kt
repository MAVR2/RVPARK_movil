package org.utl.rvpark_movil.register.data.repository

import org.utl.rvpark_movil.register.data.model.ClienteRequest
import org.utl.rvpark_movil.register.data.model.ClienteResponse
import org.utl.rvpark_movil.register.data.remote.ApiService
import org.utl.rvpark_movil.utils.RetrofitClient
import retrofit2.Retrofit

class RegisterRepository{
    private val api = RetrofitClient.createService(ApiService::class.java)

    suspend fun registrarCliente(cliente: ClienteRequest): ClienteResponse{
        return api.crearCliente(cliente)
    }
}