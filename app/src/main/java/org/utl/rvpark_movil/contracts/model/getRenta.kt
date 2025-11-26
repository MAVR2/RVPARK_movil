package org.utl.rvpark_movil.contracts.model

import org.utl.rvpark_movil.home.data.model.Renta
import org.utl.rvpark_movil.parking.data.model.RentaCompleta

data class getRentaResponse(
    val success: Boolean,
    val data: RentaCompleta,
    val message: String?,
    val error: String?
)

