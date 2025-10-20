package org.utl.rvpark_movil

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import org.utl.rvpark_movil.navigation.ui.AppNavigation
import org.utl.rvpark_movil.ui.theme.RvPark_movilTheme
import org.utl.rvpark_movil.utils.preferences.UserRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            val loggedIn = isUserLoggedIn()
            setContent {
                RvPark_movilTheme {
                    AppNavigation(startAtHome = loggedIn)
                }
            }
        }
    }

    private suspend fun isUserLoggedIn(): Boolean {
        val userRepository = UserRepository(this)
        val userEmail = userRepository.user.firstOrNull()
        Log.d("debug", "${userEmail}")
        return !userEmail.isNullOrEmpty()
    }


}
