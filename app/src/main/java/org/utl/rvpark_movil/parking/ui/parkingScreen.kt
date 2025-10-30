package org.utl.rvpark_movil.parking.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.LoteDropDown
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Composable
fun ParkingScreen(
    viewModel: ParkingViweModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }

    LaunchedEffect(Unit) {
        viewModel.loadLotes()
        viewModel.loadParks()
    }

    Scaffold { innerPadding ->
        ParkingContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            uiState = uiState,
            onLoteSelected = viewModel::updateLote,
            onParkSelected = viewModel::updatePark,
            onSave = viewModel::savePark
        )
    }
}

@Composable
fun ParkingContent(
    modifier: Modifier,
    uiState: ParkingUiState,
    onLoteSelected: (String) -> Unit,
    onParkSelected: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .fillMaxWidth(),
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo"
        )

        Spacer(Modifier.height(16.dp))

        Text(text = "Seleccione el lote")
        LoteDropDown(
            items = uiState.listaLote,
            selectedItem = uiState.lote,
            onItemSelected = onLoteSelected
        )

        Spacer(Modifier.height(16.dp))

        Text(text = "Seleccione el parqueo")
        LoteDropDown(
            items = uiState.listaPark,
            selectedItem = uiState.park,
            onItemSelected = onParkSelected
        )

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSave
        ) {
            Text("Guardar selección y continuar al pago")
        }

        if (uiState.isLoading)
            Text("Cargando...", modifier = Modifier.padding(top = 8.dp))

        if (uiState.isSuccess)
            Text("Guardado correctamente", modifier = Modifier.padding(top = 8.dp))

        uiState.error?.let {
            Text("Error: $it", modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.error)
        }
    }
}
