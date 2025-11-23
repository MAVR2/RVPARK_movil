package org.utl.rvpark_movil.parking.data.repository

import org.utl.rvpark_movil.parking.data.remote.ApiService
import org.utl.rvpark_movil.utils.RetrofitClient

class ParkingRepository {
    private val api = RetrofitClient.createService(ApiService::class.java)
    suspend fun obtenerZonas() = api.obtenerZonas()

    suspend fun apartarSpot(id: Int) = api.apartarSpot(id)

    suspend fun cancelarSpot(id: Int) = api.cancelarSpot(id)

}