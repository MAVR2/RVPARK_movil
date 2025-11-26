package org.utl.rvpark_movil.parking.data.model


data class CrearRentaRequest(
    val id_usuario: String?,
    val id_spot: String,
    val fecha_inicio: String,
    val fecha_fin: String?,
    val metodo_pago: String = "Tarjeta",
    val observaciones: String = "Esta renta es una simulacion de compra"
)


data class CrearRentaResponse(
    val success: Boolean,
    val data: RentaCompleta,
    val calculoPago: CalculoPago
)

data class RentaCompleta(
    val id_renta: Int,
    val id_usuario: Int,
    val id_spot: Int,
    val fecha_inicio: String,
    val fecha_fin: String?,
    val total_dias: Int?,
    val monto_total: Double,
    val estatus_pago: String,
    val metodo_pago: String,
    val observaciones: String?,
    val usuario: Usuario?,
    val spot: Spot?,
    val pagos: List<Pago>?
)

data class Usuario(
    val id_usuario: Int,
    val nombre_usuario: String,
    val password_hash: String,
    val rol: String,
    val id_rv_park: Int?,
    val id_Persona: Int?,
    val activo: Boolean
)



data class Pago(
    val id_pago: Int,
    val id_renta: Int,
    val fecha_pago: String,
    val monto: Double,
    val periodo: String,
    val metodo_pago: String,
    val referencia: String
)