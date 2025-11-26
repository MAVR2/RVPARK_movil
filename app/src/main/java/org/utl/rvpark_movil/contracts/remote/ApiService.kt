package org.utl.rvpark_movil.contracts.remote

import org.utl.rvpark_movil.contracts.model.getRentaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/rentas/{id}")
    suspend fun consultarRenta(
        @Path("id") id: String,
    ): getRentaResponse
}