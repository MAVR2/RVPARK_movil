package org.utl.rvpark_movil.contracts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.utl.rvpark_movil.home.ui.HomeViewModel
import org.utl.rvpark_movil.home.ui.homeUiState
import org.utl.rvpark_movil.utils.Screen
import org.utl.rvpark_movil.utils.components.ListaContratos

@Composable
fun ContractScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val items = listOf(
        Screen.Home to Icons.Default.Home,
        Screen.Contratos to Icons.Default.Description,
        Screen.Profile to Icons.Default.Person
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadContratos("3")
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
        ContractList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            uiState = uiState,
            navController = navController
        )
    }
}

@Composable
fun ContractList(
    modifier: Modifier,
    uiState: homeUiState,
    navController: NavHostController
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Todos") }

    // Filtrado de contratos
    val filteredRentas = uiState.rentas.filter { renta ->
        selectedFilter == "Todos" || renta.estatus_pago == selectedFilter
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Contratos",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(Screen.NuevoContrato.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear nuevo contrato")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Dropdown Filtro
        Box {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { expanded = true }
            ) {
                Text("Filtro: $selectedFilter")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Todos", "Pagado", "Pendiente", "Cancelado").forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedFilter = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lista filtrada
        ListaContratos(
            contratos = filteredRentas,
            navController = navController,
        )
    }
}
