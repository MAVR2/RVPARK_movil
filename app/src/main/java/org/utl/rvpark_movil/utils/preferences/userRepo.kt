package org.utl.rvpark_movil.utils.preferences

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.content.Context

class UserRepository(private val context: Context) {

    private val dataStore = context.userDataStore

    companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_LAST_NAME = stringPreferencesKey("user_last_name")
        val USER_PHONE = stringPreferencesKey("user_phone")
        val USER_ROL = stringPreferencesKey("user_rol")

    }

    // Guardar usuario
    suspend fun saveUser(
        email: String,
        name: String,
        lastName: String,
        phone: String,
        rol: String
    ) {
        dataStore.edit { prefs ->
            prefs[USER_EMAIL] = email
            prefs[USER_NAME] = name
            prefs[USER_LAST_NAME] = lastName
            prefs[USER_PHONE] = phone
            prefs[USER_ROL] =  rol
        }
    }

    // Leer usuario
    val user: Flow<String?> = dataStore.data.map { prefs ->
        prefs[USER_EMAIL]
        prefs[USER_NAME]
        prefs[USER_LAST_NAME]
        prefs[USER_PHONE]
        prefs[USER_ROL]
    }
}