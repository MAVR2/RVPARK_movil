package org.utl.rvpark_movil.home.data.remote

import org.utl.rvpark_movil.home.data.model.GetRentasResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/rentas/")
    suspend fun consultarRentas(
        @Query("id_usuario") id_usuario: String? = null,
    ): GetRentasResponse
}
