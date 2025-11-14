package org.utl.rvpark_movil.register.data.remote

import org.utl.rvpark_movil.register.data.model.ClienteRequest
import org.utl.rvpark_movil.register.data.model.ClienteResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("api/clientes")
    suspend fun crearCliente(@Body cliente: ClienteRequest): ClienteResponse
}