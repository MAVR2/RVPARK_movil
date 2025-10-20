package org.utl.rvpark_movil.utils.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PREFS_NAME = "user_prefs"

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFS_NAME)