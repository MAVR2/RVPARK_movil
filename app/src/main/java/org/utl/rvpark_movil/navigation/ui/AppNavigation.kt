package org.utl.rvpark_movil.navigation.ui

import androidx.compose.runtime.Composable

import androidx.navigation.compose.rememberNavController
import org.utl.rvpark_movil.login.ui.LoginScreen
import org.utl.rvpark_movil.register.ui.RegisterScreen
import org.utl.rvpark_movil.utils.Screen;
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.utl.rvpark_movil.ui.theme.MainScreen





@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }, onRegister = { navController.navigate(Screen.Register.route) })
        }
        composable(Screen.Register.route) {
            RegisterScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Home.route) {
            MainScreen(navController)
        }

    }
}
