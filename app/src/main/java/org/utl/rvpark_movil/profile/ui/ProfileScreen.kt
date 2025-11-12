package org.utl.rvpark_movil.profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.utl.rvpark_movil.utils.Screen
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = UserRepository(context)

    val items = listOf(
        Screen.Home to Icons.Default.Home,
        Screen.Contratos to Icons.Default.Description,
        Screen.Profile to Icons.Default.Person
    )

    LaunchedEffect(Unit) {
        viewModel.loadUser(userRepository)
    }

    Scaffold(
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
        Profile(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}


@Composable
@Preview
fun Profile(
    uiState: userUiState,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Perfil del Usuario",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Nombre del titular de la cuenta: ${uiState.name} ${uiState.lastName}")
        Text(text = "Correo: ${uiState.email}")
        Text(text = "Teléfono: ${uiState.phone}")
        Text(text = "Tipo de cuenta: ${uiState.rol}")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {navController.navigate("editarUser")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar usuario")
        }

        Text(
            text = "Métodos de pago",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "• Tarjeta Visa terminación 1234")
        Text(text = "• PayPal: usuario@mail.com")

        Spacer(modifier = Modifier.height(32.dp))



        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {navController.navigate("editarPago")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar métodos de pago")
        }
    }
}