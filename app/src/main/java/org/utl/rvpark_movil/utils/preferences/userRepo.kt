package org.utl.rvpark_movil.utils.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.utl.rvpark_movil.profile.ui.userUiState

class UserRepository(private val context: Context) {

    private val dataStore = context.userDataStore

    companion object {
        val USER_ID = stringPreferencesKey("id_usuario")
        val USER_EMAIL = stringPreferencesKey("nombre_usuario")
        val NOMBRE = stringPreferencesKey("nombre")
        val ID_RVPARK = stringPreferencesKey("id_rv_park")
        val TOKEN = stringPreferencesKey("jwt_token")
    }

    suspend fun saveUser(
        id_usuario: String,
        nombre_usuario: String,
        nombre: String,
        id_rv_park: String,
        token: String
    ) {
        dataStore.edit { prefs ->
            prefs[USER_ID] = id_usuario
            prefs[USER_EMAIL] = nombre_usuario
            prefs[NOMBRE] =nombre
            prefs[ID_RVPARK] = id_rv_park
            prefs[TOKEN] = token
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    val token: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN]
    }

    val user: Flow<String?> = dataStore.data.map { prefs ->
        prefs[USER_EMAIL]
    }

    val user2: Flow<userUiState> = dataStore.data.map { prefs ->
        userUiState(
            id = prefs[USER_ID] ?: "",
            email = prefs[USER_EMAIL] ?: ""
        )
    }

    val user_id: Flow<String?> = dataStore.data.map { prefs ->
        prefs[USER_ID]
    }

    val nombre: Flow<String?> = dataStore.data.map { prefs ->
    prefs[NOMBRE]
    }

    val user_rvpark: Flow<String?> = dataStore.data.map { prefs ->
        prefs[ID_RVPARK]
    }
}
