package org.utl.rvpark_movil.contracts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
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
import org.utl.rvpark_movil.utils.components.SearchBarContrato

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
    val searchTextFieldState = remember { TextFieldState() }

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
            searchTextFieldState = searchTextFieldState,
            onQueryChange = viewModel::updateSearchQuery,
            onReloadContratos = { viewModel.loadContratos("3") },
            navController = navController
        )
    }
}

@Composable
fun ContractList(
    modifier: Modifier,
    uiState: homeUiState,
    searchTextFieldState: TextFieldState,
    navController: NavHostController,
    onQueryChange: (String) -> Unit,
    onReloadContratos: () -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "listado de contratos",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = { navController.navigate(Screen.NuevoContrato.route) }) {
            Text("Crear nuevo contrato")
        }

        SearchBarContrato(
            textFieldState = searchTextFieldState,
            onSearch = onQueryChange,
            searchResults = uiState.rentas,
            navController = navController
        )

        if (searchTextFieldState.text.isEmpty()) {
            ListaContratos(uiState.rentas, navController)
        }
    }
}
