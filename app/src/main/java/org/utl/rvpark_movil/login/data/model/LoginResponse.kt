package org.utl.rvpark_movil.login.data.model

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val data: LoginData?
)

data class LoginData(
    val id_usuario: Int,
    val nombre_usuario: String,
    val nombre: String,
    val rol: String,
    val id_rv_park: Int,
    val token: String
)
