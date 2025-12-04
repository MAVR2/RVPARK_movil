package org.utl.rvpark_movil.parking.ui

import DialogError
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.parking.ui.steps.PagoScreen
import org.utl.rvpark_movil.parking.ui.steps.fechasScreen
import org.utl.rvpark_movil.parking.ui.steps.spotScreen
import org.utl.rvpark_movil.parking.ui.steps.zonaScreen
import org.utl.rvpark_movil.utils.components.DialogSuccess
import org.utl.rvpark_movil.utils.components.LoadingDialog
import org.utl.rvpark_movil.utils.preferences.UserRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(
    vm: ParkingViewModel = viewModel(),
    nav: NavHostController,
    repoUser: UserRepository
) {
    val uiState by vm.uiState.collectAsState()
    val usuario by repoUser.user2.collectAsState(initial = null)

    val loading = vm.uiState.collectAsState().value.isLoading


    var mostrarFechaInicio by remember { mutableStateOf(false) }
    var mostrarFechaFin by remember { mutableStateOf(false) }
    var mostrarErrorFecha by remember { mutableStateOf(false) }
    var mostrarErrorFechaFin by remember { mutableStateOf(false) }
    var cancelar by remember { mutableStateOf(false) }
    var confirmar by remember { mutableStateOf(false) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    var tiempo by remember { mutableStateOf(300) }
    var noTiempo by remember { mutableStateOf(false) }

    val stateInicio = rememberDatePickerState()
    val stateFin = rememberDatePickerState()

    val zonaSeleccionada = uiState.zonaSeleccionada
    val spotSeleccionado = uiState.spotSeleccionado

    LaunchedEffect(Unit) {
        vm.cargarZonas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5ECEF))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState.paso) {
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

            3, 4 -> {
                LaunchedEffect(Unit) {
                    while (tiempo > 0) {
                        delay(1000)
                        tiempo--
                    }
                    noTiempo = true
                }

                if (uiState.paso == 4) {
                    LaunchedEffect(Unit) {
                        vm.obtenerCalculoRenta()
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            when (uiState.paso) {

                1 -> zonaScreen(
                    paso = uiState.paso,
                    uiState = uiState,
                    vm = vm,
                    onCancelar = { cancelar = true }
                )

                2 -> spotScreen(
                    paso = uiState.paso,
                    uiState = uiState,
                    vm = vm,
                    onAtras = { vm.retrocederPaso() },
                    onSiguiente = {
                        if (uiState.spotSeleccionado != null) {
                            vm.apartarSpot(uiState.spotSeleccionado!!.id_spot)
                            mostrarDialogo = true
                        }
                    }
                )

                3 -> {
                    if (noTiempo) {
                        DialogError(
                            titulo = "Se terminó el tiempo",
                            texto = "El proceso expiró.",
                            onDismiss = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                            },
                            onConfirm = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                            },
                            icon = Icons.Default.Error
                        )
                    }

                    fechasScreen(
                        minutos = tiempo / 60,
                        segundos = tiempo % 60,
                        uiState = uiState,
                        vm = vm,
                        stateInicio = stateInicio,
                        stateFin = stateFin,
                        onMostrarFechaInicio = { mostrarFechaInicio = true },
                        onMostrarFechaFin = { mostrarFechaFin = true },
                        onCancelar = { cancelar = true }
                    )
                }

                4 -> PagoScreen(
                    minutos = tiempo / 60,
                    segundos = tiempo % 60,
                    uiState = uiState,
                    vm = vm,
                    repoUser = repoUser,
                    onCancelar = { cancelar = true },
                    onPagar = { confirmar = true }
                )

                5 -> nav.navigate("home")
            }
        }
    }

    // DIALOGOS FECHAS
    if (mostrarFechaInicio) {
        DatePickerDialog(
            onDismissRequest = {
                mostrarFechaInicio = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val selected = stateInicio.selectedDateMillis
                    val today = startOfTodayMillis()

                    if (selected == null || selected < today) {
                        mostrarFechaInicio = false
                        mostrarErrorFecha = true
                    } else {
                        vm.seleccionarFechaInicio(millisToMysqlDate(selected))
                        mostrarFechaInicio = false
                    }
                }) {
                    Text("OK")
                }
            }
        ) { DatePicker(state = stateInicio) }
    }

    if (mostrarFechaFin) {
        DatePickerDialog(
            onDismissRequest = {
                mostrarFechaFin = false
                stateFin.selectedDateMillis = null
            },
            confirmButton = {
                TextButton(onClick = {
                    val selected = stateFin.selectedDateMillis
                    val inicioSelected = stateInicio.selectedDateMillis
                    val today = startOfTodayMillis()

                    when {
                        selected == null -> mostrarErrorFechaFin = true
                        selected < today -> mostrarErrorFechaFin = true
                        inicioSelected != null && selected < inicioSelected -> mostrarErrorFechaFin = true
                        else -> {
                            vm.seleccionarFechaFin(millisToMysqlDate(selected))
                            mostrarFechaFin = false
                        }
                    }
                }) { Text("OK") }
            }
        ) { DatePicker(state = stateFin) }
    }

    if (mostrarErrorFecha) {
        DialogError(
            titulo = "Fecha inválida",
            texto = "La fecha de inicio no puede ser anterior a hoy.",
            onDismiss = {
                mostrarErrorFecha = false
                mostrarFechaInicio = true
            },
            onConfirm = {
                mostrarErrorFecha = false
                mostrarFechaInicio = true
            },
            icon = Icons.Default.Error
        )
    }

    if (mostrarErrorFechaFin) {
        DialogError(
            titulo = "Fecha inválida",
            texto = "La fecha de fin no es válida.",
            onDismiss = {
                mostrarErrorFechaFin = false
                mostrarFechaFin = true
            },
            onConfirm = {
                mostrarErrorFechaFin = false
                mostrarFechaFin = true
            },
            icon = Icons.Default.Error
        )
    }

    if (mostrarDialogo) {
        DialogSuccess(
            titulo = "Continuar",
            texto = "Tendrás 5 minutos para completar el proceso.",
            icon = Icons.Default.Info,
            onConfirm = {
                mostrarDialogo = false
                vm.siguientePaso()
            },
            onCancel = {
                mostrarDialogo = false
                nav.navigate("home")
            }
        )
    }

    if (loading) {
        LoadingDialog(message = "Procesando...")
    }


    if (confirmar) {
        DialogSuccess(
            titulo = "Confirmar pago",
            texto = "¿Los datos son correctos?",
            icon = Icons.Default.Info,
            onConfirm = {
                confirmar = false
                vm.crearRenta(usuario?.id)
            },
            onCancel = { confirmar = false }
        )
    }

}

fun millisToMysqlDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun startOfTodayMillis(): Long {
    return LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
