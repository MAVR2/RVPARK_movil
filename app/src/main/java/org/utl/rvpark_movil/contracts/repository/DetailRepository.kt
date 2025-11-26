package org.utl.rvpark_movil.contracts.repository

import org.utl.rvpark_movil.contracts.remote.ApiService
import org.utl.rvpark_movil.utils.RetrofitClient

class DetailRepository {
    private val api: ApiService = RetrofitClient.createService(ApiService::class.java)

    suspend fun consultarRenta(id: String) = api.consultarRenta(id)

}