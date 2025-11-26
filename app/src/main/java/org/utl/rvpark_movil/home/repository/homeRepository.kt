package org.utl.rvpark_movil.home.repository

import org.utl.rvpark_movil.home.data.model.GetRentasResponse
import org.utl.rvpark_movil.home.data.remote.ApiService
import org.utl.rvpark_movil.utils.RetrofitClient

class HomeRepository {
    private val api: ApiService = RetrofitClient.createService(ApiService::class.java)


    suspend fun consultarRentas(
        id_usuario: String? = null,
    ): GetRentasResponse {
        return api.consultarRentas(
            id_usuario = id_usuario,
        )
    }
}
