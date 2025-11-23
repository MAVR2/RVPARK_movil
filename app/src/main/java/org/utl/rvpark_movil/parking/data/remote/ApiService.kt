package org.utl.rvpark_movil.parking.data.remote

import org.utl.rvpark_movil.parking.data.model.RentaResponse
import org.utl.rvpark_movil.parking.data.model.ZonasResponse
import org.utl.rvpark_movil.parking.data.model.rentaCalRequest
import org.utl.rvpark_movil.parking.data.model.spotResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService{
    @GET("api/spots/zonas")
    suspend fun obtenerZonas(): ZonasResponse

    @POST("api/spots/apartar-spot/{id}")
    suspend fun apartarSpot(
        @Path("id") id: Int
    ): spotResponse

    @POST("api/spots/cancelar-spot/{id}")
    suspend fun cancelarSpot(
        @Path("id") id: Int
    ): spotResponse

    @POST("api/rentas/calcular")
    suspend fun calcularRenta(@Body fechas: rentaCalRequest): RentaResponse
}