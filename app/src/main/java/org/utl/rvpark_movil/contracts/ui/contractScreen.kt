package org.utl.rvpark_movil.contracts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.utl.rvpark_movil.home.ui.HomeViewModel
import org.utl.rvpark_movil.home.ui.homeUiState
import org.utl.rvpark_movil.utils.Screen
import org.utl.rvpark_movil.utils.components.SearchBarContrato
import org.utl.rvpark_movil.utils.preferences.UserRepository


@Composable
fun contractScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()


) {
    val items = listOf(
        Screen.Home to Icons.Default.Home,
        Screen.Contratos to Icons.Default.Description,
        Screen.Profile to Icons.Default.Person
    )

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }

    val searchTextFieldState = remember { TextFieldState() }


    LaunchedEffect(Unit) {
        viewModel.loadContratos(userRepository)
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
    )
    { innerPadding ->
        ContractList(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            uiState = uiState,
            searchTextFieldState = searchTextFieldState,
            onQueryChange = viewModel::updateSearchQuery,
            navController = navController,
            onReloadContratos = { viewModel.loadContratos(userRepository) })
    }
}



@Composable
fun ContractList(
    modifier: Modifier,
    uiState: homeUiState,
    onReloadContratos: () -> Unit,
    searchTextFieldState: TextFieldState,
    onQueryChange: (String) -> Unit,
    navController: NavHostController // agrega esto
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "listado de contratos",
            modifier = Modifier.padding(top = 8.dp)
        )

        SearchBarContrato(
            textFieldState = searchTextFieldState,
            onSearch = onQueryChange,
            searchResults = uiState.contratos.orEmpty(),
            navController = navController
        )


    }
}
