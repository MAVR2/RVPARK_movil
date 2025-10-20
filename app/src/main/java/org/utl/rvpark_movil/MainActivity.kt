package org.utl.rvpark_movil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.utl.rvpark_movil.ui.theme.RvPark_movilTheme
import org.utl.rvpark_movil.navigation.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RvPark_movilTheme {
                AppNavigation()
            }

        }
    }
}