package org.utl.rvpark_movil.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.utils.preferences.UserRepository
import org.utl.rvpark_movil.utils.components.Carousel
import org.utl.rvpark_movil.utils.components.ListaContratos

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onContrato: () -> Unit ={}
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = UserRepository(context)

    LaunchedEffect(Unit) {
        viewModel.loadContratos(userRepository)
    }

    Box(
        Modifier.fillMaxSize()
            .padding(16.dp)){
        Home(
            Modifier.align(Alignment.Center),
            uiState = uiState,
            onContrato = {
                viewModel.loadContratos(userRepository)
            }
        )
    }
}

@Composable
fun Home(
    modifier: Modifier,
    uiState: homeUiState,
    onContrato: () -> Unit
    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Lo nuevo en RVPARK"
        )

        Spacer(Modifier.height(12.dp))

        Carousel()

        Spacer(Modifier.height(24.dp))

        ListaContratos(contratos = uiState.contratos)
    }
}

