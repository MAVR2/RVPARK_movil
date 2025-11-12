package org.utl.rvpark_movil.register.data.model

data class ClienteRequest(
    val nombre: String,
    val telefono: String,
    val email: String,
    val direccion: String,
)