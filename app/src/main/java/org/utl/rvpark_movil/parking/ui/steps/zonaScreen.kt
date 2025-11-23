package org.utl.rvpark_movil.parking.ui.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.parking.ui.ParkingUiState
import org.utl.rvpark_movil.parking.ui.ParkingViewModel
import org.utl.rvpark_movil.utils.components.PasoProgressBar
import org.utl.rvpark_movil.utils.components.ZonaDropdown

@Composable
fun zonaScreen(
    paso: Int,
    uiState: ParkingUiState,
    vm: ParkingViewModel,
    onCancelar: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasoProgressBar(paso)

        Text(
            "Selecciona una zona",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        ZonaDropdown(
            zonas = uiState.zonas,
            seleccion = uiState.zonas.firstOrNull { it.nombre == uiState.zonaSeleccionada },
            onSelect = { zona ->
                vm.seleccionarZona(zona.nombre)
            }
        )

        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    onCancelar()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )

            )
            {
                Text("Cancelar")
            }

            Button(
                onClick = { if (uiState.zonaSeleccionada != null) vm.siguientePaso() },
                enabled = uiState.zonaSeleccionada != null
            ) { Text("Siguiente") }
        }


    }
}