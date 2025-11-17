package org.utl.rvpark_movil.parking.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.SpotDropdown
import org.utl.rvpark_movil.utils.components.ZonaDropdown


@Composable
fun ParkingScreen(vm: ParkingViewModel = viewModel()) {

    val zonasUi by vm.zonasUi.collectAsState()
    val zonas by vm.zonas.collectAsState()
    val paso by vm.paso.collectAsState()
    val zonaSeleccionada by vm.zonaSeleccionada.collectAsState()
    val spotSeleccionado by vm.spotSeleccionado.collectAsState()

    LaunchedEffect(Unit) { vm.cargarZonas() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5ECEF))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(paso){
            1 ->
                {
                    Image(
                        painter = painterResource(R.drawable.mapa_completo),
                        contentDescription = "",
                        modifier = Modifier.size(500.dp)
                    )

                }
            2 ->
                {

                }

        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            when (paso) {

                1 -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    PasoProgressBar(paso)

                    Text(
                        "Selecciona una zona",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    ZonaDropdown(
                        zonas = zonasUi,
                        seleccion = zonasUi.firstOrNull() { it.nombre == zonaSeleccionada},
                        onSelect = { zona ->
                            vm.zonaSeleccionada.value   = zona.nombre }
                    )

                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = { /* Cancelar */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )

                        )
                        {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = { if (zonaSeleccionada != null) vm.siguientePaso() },
                            enabled = zonaSeleccionada != null
                        ) { Text("Siguiente") }
                    }


                }

                2 -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    PasoProgressBar(paso)


                    Text(
                        "Selecciona un spot",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    val spots = zonas[zonaSeleccionada] ?: emptyList()

                    SpotDropdown(
                        spots = spots,
                        seleccion = spotSeleccionado,
                        onSelect = { vm.spotSeleccionado.value = it }
                    )

                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(onClick = { vm.retrocederPaso() }) {
                            Text("Atrás")
                        }

                        Button(
                            onClick = { if (spotSeleccionado != null) vm.siguientePaso() },
                            enabled = spotSeleccionado != null
                        ) { Text("Siguiente") }
                    }

                }

                3 -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    PasoProgressBar(paso)


                    Text(
                        "Confirmar y pagar",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    Text("Zona: $zonaSeleccionada")
                    Text("Spot: ${spotSeleccionado?.codigo_spot}")

                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(onClick = { vm.retrocederPaso() }) {
                            Text("Atrás")
                        }

                        Button(onClick = { /* Pago */ }) {
                            Text("Pagar")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PasoProgressBar(paso: Int) {
    val progreso = paso / 3f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = "Paso $paso de 3",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        androidx.compose.material3.LinearProgressIndicator(
            progress = progreso,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}











