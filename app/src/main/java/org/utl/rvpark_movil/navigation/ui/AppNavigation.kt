package org.utl.rvpark_movil.navigation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.utl.rvpark_movil.login.ui.LoginScreen
import org.utl.rvpark_movil.register.ui.RegisterScreen
import org.utl.rvpark_movil.ui.theme.MainScreen
import org.utl.rvpark_movil.utils.Screen

@Composable
fun AppNavigation(startAtHome: Boolean = false) {
    val navController = rememberNavController()
    val startDestination = if (startAtHome) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }},
                onRegister = { navController.navigate(Screen.Register.route) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Home.route) {
            MainScreen(navController)
        }

    }
}
