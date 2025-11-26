package org.utl.rvpark_movil.profile.data.repository

import android.util.Log
import org.utl.rvpark_movil.profile.data.model.Persona
import org.utl.rvpark_movil.profile.data.model.personaResponse
import org.utl.rvpark_movil.utils.RetrofitClient
import org.utl.rvpark_movil.profile.data.remote.ApiService


class PersonaRepository {

    private val api = RetrofitClient.createService(ApiService::class.java)

    suspend fun updatePersona(persona: Persona): personaResponse {
        Log.d("debug", persona.id_Persona)
        return api.updatePersona(
            id = persona.id_Persona,
            persona = persona
        )
    }
}




