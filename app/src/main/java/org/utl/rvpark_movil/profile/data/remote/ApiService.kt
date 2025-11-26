package org.utl.rvpark_movil.profile.data.remote

import org.utl.rvpark_movil.profile.data.model.Persona
import org.utl.rvpark_movil.profile.data.model.personaResponse
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @PUT("api/clientes/{id}")
    suspend fun updatePersona(
        @Path("id") id: String,
        @Body persona: Persona
    ): personaResponse
}