package org.utl.rvpark_movil.profile.data.model


data class Persona(
    val id_Persona: String,
    val nombre: String?,
    val telefono: String?,
    val email: String?,
    val vehiculo: String?,
    val direccion: String?,
    val fecha_registro: String?,
)

data class personaResponse(
    val success: Boolean,
    val data: Persona,
    val message: String?,
    val error: String?
)