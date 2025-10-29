package org.utl.rvpark_movil.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.utl.rvpark_movil.home.ui.HomeScreen

import org.utl.rvpark_movil.utils.Screen

@Preview
@Composable
fun MainScreen(navController: NavHostController) {
    val items = listOf(
        Screen.Home to Icons.Default.Home,
        Screen.Contratos to Icons.Default.Description,
        Screen.Profile to Icons.Default.Person
    )

    Scaffold(
        topBar = { },
        bottomBar = {
            NavigationBar {
                val current = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { (screen, icon) ->
                    NavigationBarItem(
                        selected = current == screen.route,
                        onClick = {
                            if (current != screen.route) {
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(Screen.Home.route) { saveState = true }
                                }
                            }
                        },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(screen.route.substringAfter("/")) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Text("Bienvenido a Home")
            HomeScreen(navController)
        }
    }
}

