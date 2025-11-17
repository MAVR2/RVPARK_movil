package org.utl.rvpark_movil.parking.data.model


data class Spot(
    val id_spot: Int,
    val id_rv_park: Int,
    val codigo_spot: String,
    val estado: String,
    val zona: String,
    val fecha_actualizacion: String
)

data class ZonasResponse(
    val success: Boolean,
    val data: Map<String, List<Spot>>
)
