package org.utl.rvpark_movil.register.data.model

data class ClienteRequest(
    val nombre: String,
    val telefono: String,
    val email: String,
    val direccion: String,
    val nombre_usuario: String,
    val password_hash: String,
    val rol: String
)