package org.utl.rvpark_movil.parking.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.LoteDropDown
import org.utl.rvpark_movil.utils.preferences.UserRepository
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday


@Composable
fun ParkingScreen(
    viewModel: ParkingViweModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }

    LaunchedEffect(Unit) {
        viewModel.loadZonas()
        viewModel.loadParks()
    }

    Scaffold { innerPadding ->
        ParkingContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            uiState = uiState,
            onLoteSelected = viewModel::updateZona,
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
    val context = LocalContext.current
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    val calendario = Calendar.getInstance()

    val datePickerInicio = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaInicio = "$dayOfMonth/${month + 1}/$year"
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    val datePickerFin = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaFin = "$dayOfMonth/${month + 1}/$year"
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    LazyColumn(modifier = modifier.padding(16.dp)) {
        item {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.map),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            Text("Seleccione la zona")
            LoteDropDown(
                items = uiState.listaZona,
                selectedItem = uiState.lote,
                onItemSelected = onLoteSelected
            )

            Spacer(Modifier.height(16.dp))

            Text("Seleccione el lugar (solo se muestran los que están disponibles)")
            LoteDropDown(
                items = uiState.listaPark,
                selectedItem = uiState.park,
                onItemSelected = onParkSelected
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = {},
                label = { Text("Fecha de inicio") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { datePickerInicio.show() }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Seleccionar fecha inicio"
                        )
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fechaFin,
                onValueChange = {},
                label = { Text("Fecha final") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { datePickerFin.show() }) {
                        Icon(
                            imageVector = Icons.Filled.CalendarToday,
                            contentDescription = "Seleccionar fecha final"
                        )
                    }
                }
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
                Text(
                    "Error: $it",
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}