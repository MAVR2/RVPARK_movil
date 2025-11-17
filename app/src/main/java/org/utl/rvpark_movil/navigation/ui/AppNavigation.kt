package org.utl.rvpark_movil.navigation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.utl.rvpark_movil.contracts.ui.ContractScreen
import org.utl.rvpark_movil.login.ui.LoginScreen
import org.utl.rvpark_movil.register.ui.RegisterScreen
import org.utl.rvpark_movil.ui.theme.MainScreen
import org.utl.rvpark_movil.utils.Screen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.utl.rvpark_movil.chat.ui.ChatScreen
import org.utl.rvpark_movil.contracts.ui.ContratoDetailScreen
import org.utl.rvpark_movil.parking.ui.ParkingScreen
import org.utl.rvpark_movil.profile.ui.EditarPagoScreen
import org.utl.rvpark_movil.profile.ui.EditarUserScreen
import org.utl.rvpark_movil.profile.ui.ProfileScreen


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

        composable(Screen.Contratos.route){
            ContractScreen(navController )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(
            route = "contratoDetalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val contratoId = backStackEntry.arguments?.getInt("id") ?: 0
            ContratoDetailScreen(contratoId)
        }

        composable(Screen.NuevoContrato.route) {
            ParkingScreen(nav= navController)
        }

        composable(Screen.ChatBot.route){
            ChatScreen()
        }

        composable(Screen.EditarUser.route){
            EditarUserScreen(navController = navController)
        }

        composable((Screen.EditarPago.route)){
            EditarPagoScreen(navController = navController)
        }


    }
}
