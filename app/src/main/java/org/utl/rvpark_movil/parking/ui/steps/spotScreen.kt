package org.utl.rvpark_movil.parking.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.parking.ui.ParkingUiState
import org.utl.rvpark_movil.parking.ui.ParkingViewModel
import org.utl.rvpark_movil.utils.components.PasoProgressBar
import org.utl.rvpark_movil.utils.components.SpotDropdown

@Composable
fun spotScreen(
    paso: Int,
    uiState: ParkingUiState,
    vm: ParkingViewModel,
    onAtras: () -> Unit,
    onSiguiente: () -> Unit

){
   Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasoProgressBar(paso)


        Text(
            "Selecciona un spot",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        val spots = uiState.zonas
            .firstOrNull { it.nombre == uiState.zonaSeleccionada }
            ?.spots ?: emptyList()


        SpotDropdown(
            spots = spots,
            seleccion = uiState.spotSeleccionado,
            onSelect = { vm.seleccionarSpot(it) }
        )

        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(onClick = {
                    onAtras()
            }) {
                Text("Atrás")
            }

            Button(
                onClick = {
                    onSiguiente()
                },
                enabled = uiState.spotSeleccionado != null
            ) { Text("Siguiente") }
        }

    }
}