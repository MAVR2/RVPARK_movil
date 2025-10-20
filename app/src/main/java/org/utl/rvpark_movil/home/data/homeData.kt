package org.utl.rvpark_movil.home.data

import kotlinx.coroutines.flow.firstOrNull
import org.utl.rvpark_movil.utils.preferences.UserRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

data class Contrato(
    val id_renta: Int,
    val id_cliente: Int,
    val id_spot: Int,
    val fecha_inicio: String,
    val fecha_fin: String,
    val total_dias: Int,
    val monto_total: Double,
    val estatus_pago: String,
    val metodo_pago: String,
    val observaciones: String
)

val hoy = LocalDate.now()
val formatter = DateTimeFormatter.ISO_DATE

fun generarContratos(cantidad: Int = 10): List<Contrato> {
    val hoy = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    return List(cantidad) { i ->
        val diasDuracion = Random.nextInt(1, 15)
        val diasAtras = Random.nextInt(0, 4) // Entre hoy y hace 3 días
        val fechaInicio = hoy.minusDays(diasAtras.toLong())
        val fechaFin = fechaInicio.plusDays(diasDuracion.toLong())
        val monto = Random.nextDouble(500.0, 5000.0)
        val estatus = if (Random.nextBoolean()) "Pagado" else "Pendiente"
        val metodo = listOf("Tarjeta", "Efectivo", "Transferencia").random()
        val observaciones = listOf("Sin observaciones", "Pago parcial", "Pago al llegar").random()

        Contrato(
            id_renta = i + 1,
            id_cliente = 1, // fijo
            id_spot = Random.nextInt(1, 20),
            fecha_inicio = fechaInicio.format(formatter),
            fecha_fin = fechaFin.format(formatter),
            total_dias = diasDuracion,
            monto_total = monto,
            estatus_pago = estatus,
            metodo_pago = metodo,
            observaciones = observaciones
        )
    }
}

val contratos = generarContratos(10)


suspend fun getContratos(userRepository: UserRepository): List<Contrato>? {
    val idCliente = userRepository.user_id.firstOrNull() ?: return null

    return if (idCliente == "1") contratos else null
}




