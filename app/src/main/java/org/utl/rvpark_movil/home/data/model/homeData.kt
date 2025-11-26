package org.utl.rvpark_movil.home.data.model

import org.utl.rvpark_movil.parking.data.model.Spot


data class Pago(
    val id_pago: Int,
    val id_renta: Int,
    val fecha_pago: String?,
    val monto: Double,
    val periodo: String?,
    val metodo_pago: String?,
    val referencia: String?
)

data class Usuario(
    val id_usuario: Int,
    val nombre_usuario: String?,
    val correo: String?
)

data class Renta(
    val id_renta: Int,
    val id_usuario: Int,
    val id_spot: Int,
    val fecha_inicio: String?,
    val fecha_fin: String?,
    val total_dias: Int?,
    val monto_total: Double,
    val estatus_pago: String,
    val metodo_pago: String?,
    val observaciones: String?,

    val usuario: Usuario?,
    val spot: Spot?,
    val pagos: List<Pago>?
)

data class GetRentasResponse(
    val success: Boolean,
    val count: Int,
    val data: List<Renta>
)




