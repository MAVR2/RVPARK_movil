package org.utl.rvpark_movil.utils

sealed class Screen(val route: String){
    object Login: Screen("login")
    object Register: Screen("register")
    object Home: Screen("home")
    object Profile: Screen("profile")
    object Contratos: Screen("contratos")

    object NuevoContrato: Screen("nuevoContrato")

    object ChatBot: Screen("chatBot")

    object EditarUser: Screen("editarUser")


}