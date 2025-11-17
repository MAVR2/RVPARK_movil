package org.utl.rvpark_movil.parking.data.remote

import org.utl.rvpark_movil.parking.data.model.ZonasResponse
import retrofit2.http.GET

interface ApiService{
    @GET("api/spots/zonas")
    suspend fun obtenerZonas(): ZonasResponse

}