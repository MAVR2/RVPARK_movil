package org.utl.rvpark_movil.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.rememberNavController
import org.utl.rvpark_movil.profile.ui.userUiState
import org.utl.rvpark_movil.utils.preferences.UserRepository
import org.utl.rvpark_movil.utils.components.HeroCarousel
import org.utl.rvpark_movil.utils.components.ListaContratos

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel(),
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }
    val nombre by userRepository.nombre.collectAsState(initial = "")



    LaunchedEffect(Unit) {
        viewModel.loadContratos(userRepository)
        viewModel.setNombre(nombre ?: "")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            ChatBotFAB (onClick = { navHostController.navigate("chatBot") })
        }
    ) { paddingValues ->

        Home(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background),
        uiState = uiState,
            navHostController = navHostController,
        )
    }
}

@Composable
fun Home(
    modifier: Modifier,
    uiState: homeUiState,
    navHostController: NavHostController,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Bienvenido: ${uiState.nombre_usuario}",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            textAlign = TextAlign.Center,
            text = "Lo nuevo en RVPARK",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(Modifier.height(12.dp))

        HeroCarousel()

        Spacer(Modifier.height(24.dp))

        ListaContratos(
            contratos = uiState.contratos,
            navHostController = navHostController)


        Spacer(Modifier.height(100.dp))
    }
}

@Composable
fun ChatBotFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.Message, "Chatbot RVPARK")
    }
}