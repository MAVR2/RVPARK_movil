package org.utl.rvpark_movil.parking.data.model

import android.os.Message

data class rentaCalRequest(
    val fecha_inicio: String,
    val fecha_fin : String?
)



data class RentaResponse(
    val success: Boolean,
    val message: String?,
    val data: RentaData?,
    val calculoPago: CalculoPago?,
    val error: String?
)

data class RentaData(
    val total_dias: Int?
)

data class CalculoPago(
    val monto: Double,
    val diasRestantes: Int,
    val ultimoDiaMes: String,
    val periodo: String
)
