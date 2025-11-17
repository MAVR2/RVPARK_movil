package org.utl.rvpark_movil.parking.ui

import DialogError
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.DialogSuccess
import org.utl.rvpark_movil.utils.components.SpotDropdown
import org.utl.rvpark_movil.utils.components.ZonaDropdown
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(
    vm: ParkingViewModel = viewModel(),
    nav: NavHostController
) {

    val zonasUi by vm.zonasUi.collectAsState()
    val zonas by vm.zonas.collectAsState()

    var mostrarFechaInicio by remember { mutableStateOf(false) }
    var mostrarFechaFin by remember { mutableStateOf(false) }

    val stateInicio = rememberDatePickerState()
    val stateFin = rememberDatePickerState()

    val paso by vm.paso.collectAsState()
    val zonaSeleccionada by vm.zonaSeleccionada.collectAsState()
    val spotSeleccionado by vm.spotSeleccionado.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }


    val mapaRes = when (zonaSeleccionada) {
        "A" -> R.drawable.mapa_a
        "B" -> R.drawable.mapa_b
        "C" -> R.drawable.mapa_c
        "D" -> R.drawable.mapa_a
        "F" -> R.drawable.mapa_b
        "G" -> R.drawable.mapa_c
        "H" -> R.drawable.mapa_c
        "I" -> R.drawable.mapa_a
        "J" -> R.drawable.mapa_b
        "K" -> R.drawable.mapa_c
        "L" -> R.drawable.mapa_a
        "M" -> R.drawable.mapa_b
        "N" -> R.drawable.mapa_c
        "O" -> R.drawable.mapa_o
        else -> null
    }

    LaunchedEffect(Unit) { vm.cargarZonas() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5ECEF))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (paso) {
            1 -> {
                Image(
                    painter = painterResource(R.drawable.mapa_completo),
                    contentDescription = "",
                    modifier = Modifier.size(500.dp)
                )

            }

            2 -> {
                val mapaRes = when (zonaSeleccionada) {
                    "A" -> R.drawable.mapa_a
                    "B" -> R.drawable.mapa_b
                    "C" -> R.drawable.mapa_c
                    "D" -> R.drawable.mapa_d
                    "E" -> R.drawable.mapa_e
                    "F" -> R.drawable.mapa_f
                    "G" -> R.drawable.mapa_g
                    "H" -> R.drawable.mapa_h
                    "I" -> R.drawable.mapa_i
                    "J" -> R.drawable.mapa_j
                    "K" -> R.drawable.mapa_k
                    "L" -> R.drawable.mapa_l
                    "M" -> R.drawable.mapa_m
                    "N" -> R.drawable.mapa_n
                    "O" -> R.drawable.mapa_o
                    else -> null
                }

                mapaRes?.let {
                    Image(
                        painter = painterResource(it),
                        contentDescription = "",
                        modifier = Modifier.size(500.dp)
                    )
                }
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
                        seleccion = zonasUi.firstOrNull() { it.nombre == zonaSeleccionada },
                        onSelect = { zona ->
                            vm.zonaSeleccionada.value = zona.nombre
                        }
                    )

                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = { nav.navigate("home") },
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

                        Button(onClick = {
                            vm.retrocederPaso()
                            vm.spotSeleccionado.value = null
                        }) {
                            Text("Atrás")
                        }

                        Button(
                            onClick = {
                                mostrarDialogo = true
                                if (spotSeleccionado != null) {
                                    mostrarDialogo = true
                                }
                            },
                            enabled = spotSeleccionado != null
                        ) { Text("Siguiente") }
                    }

                }

                3 -> {

                    if (paso == 3) {

                        // Cronómetro 5 min (300s)
                        var tiempo by remember { mutableStateOf(300) }

                        LaunchedEffect(Unit) {
                            while (tiempo > 0) {
                                delay(1000)
                                tiempo--
                            }
                        }

                        val minutos = tiempo / 60
                        val segundos = tiempo % 60


                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            PasoProgressBar(paso)

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = String.format("%02d:%02d", minutos, segundos),
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Text(
                                "Confirme la informacion",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )

                            Text("Zona: $zonaSeleccionada")
                            Text("Spot: ${spotSeleccionado?.codigo_spot}")

                            // Mostrar fechas seleccionadas
                            Text(
                                "Inicio: " + (stateInicio.selectedDateMillis?.let {
                                    Instant.ofEpochMilli(it)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                } ?: "No seleccionada"),
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Text(
                                "Fin: " + (stateFin.selectedDateMillis?.let {
                                    Instant.ofEpochMilli(it)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                } ?: "No seleccionada"),
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Button(onClick = { mostrarFechaInicio = true }) {
                                    Text("Fecha inicio")
                                }

                                Button(onClick = { mostrarFechaFin = true }) {
                                    Text("Fecha fin")
                                }
                            }



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
    }

    // DIÁLOGO FECHA INICIO
    if (mostrarFechaInicio) {
        DatePickerDialog(
            onDismissRequest = { mostrarFechaInicio = false },
            confirmButton = {
                TextButton(onClick = {
                    mostrarFechaInicio = false
                    vm.fechaInicio.value = stateInicio.selectedDateMillis?.let { millisToMysqlDate(it) }
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = stateInicio)
        }
    }

// DIÁLOGO FECHA FIN
    if (mostrarFechaFin) {
        DatePickerDialog(
            onDismissRequest = { mostrarFechaFin = false },
            confirmButton = {
                TextButton(onClick = {
                    mostrarFechaFin = false
                    vm.fechaFin.value = stateFin.selectedDateMillis?.let { millisToMysqlDate(it) }
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = stateFin)
        }
    }




    if(mostrarDialogo){
        DialogSuccess(
            titulo = "Espera",
            texto = "si continuas tendras 5 minutos para completar el proceso, de lo contrario el contrato se cancelara automaticamente.",
            icon = Icons.Default.Info,
            onConfirm = {
                vm.siguientePaso()
                mostrarDialogo =false
                        },
            onCancel = {
                nav.navigate("home")
                mostrarDialogo = false
            }
        )
    }
}

fun millisToMysqlDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
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

        LinearProgressIndicator(
            progress = progreso,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}











