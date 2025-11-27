package org.utl.rvpark_movil.parking.ui

import DialogError
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.parking.ui.steps.PagoScreen
import org.utl.rvpark_movil.parking.ui.steps.fechasScreen
import org.utl.rvpark_movil.parking.ui.steps.spotScreen
import org.utl.rvpark_movil.parking.ui.steps.zonaScreen
import org.utl.rvpark_movil.utils.components.DialogSuccess
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import org.utl.rvpark_movil.utils.preferences.UserRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(
    vm: ParkingViewModel = viewModel(),
    nav: NavHostController,
    repoUser: UserRepository
) {

    val uiState by vm.uiState.collectAsState()

    var mostrarFechaInicio by remember { mutableStateOf(false) }
    var mostrarFechaFin by remember { mutableStateOf(false) }

    var mostrarErrorFecha by remember { mutableStateOf(false) }
    var mostrarerrorFechaFin by remember { mutableStateOf(false) }

    var cancelar by remember { mutableStateOf(false) }

    var confirmar by remember { mutableStateOf(false) }

    val usuario by repoUser.user2.collectAsState(initial = null)



    var tiempo by remember { mutableStateOf(300) }
    var noTiempo by remember { mutableStateOf(false) }

    var stateInicio = rememberDatePickerState()

    var stateFin = rememberDatePickerState(
        initialSelectedDateMillis = null
    )



    val zonaSeleccionada = uiState.zonaSeleccionada
    val spotSeleccionado = uiState.spotSeleccionado


    var mostrarDialogo by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) { vm.cargarZonas() }

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
            3  ->{
                LaunchedEffect(Unit) {
                    while (tiempo > 0) {
                        delay(1000)
                        tiempo--
                    }
                    noTiempo = true
                }
            }
            4 -> {
                LaunchedEffect(Unit) {
                    while (tiempo > 0) {
                        delay(1000)
                        tiempo--
                    }
                    noTiempo = true
                }
                LaunchedEffect(Unit) {
                    vm.obtenerCalculoRenta()
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

                1 ->{
                    zonaScreen(
                        paso = uiState.paso,
                        uiState = uiState,
                        vm = vm,
                        onCancelar = {
                            cancelar = true
                        }
                    )
                }

                2 ->{
                    spotScreen(
                        paso = uiState.paso,
                        uiState = uiState,
                        vm = vm,
                        onAtras = {
                            vm.retrocederPaso()
                        },
                        onSiguiente = {
                            mostrarDialogo = true
                            if (uiState.spotSeleccionado != null) {
                                mostrarDialogo = true
                            }
                            uiState.spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                        }
                    )
                }

                3 -> {
                    if (noTiempo) {
                        mostrarDialogo = false
                        mostrarErrorFecha = false
                        mostrarerrorFechaFin = false
                        mostrarFechaInicio = false
                        mostrarFechaFin = false
                        DialogError(
                            onDismiss = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                            },
                            onConfirm = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }

                            },
                            titulo = "Se terminó el tiempo para solicitar el spot",
                            texto = "Por favor vuelve a intentarlo.",
                            icon = Icons.Default.Error
                        )
                    }

                    val minutos = tiempo / 60
                    val segundos = tiempo % 60

                    fechasScreen(
                        minutos = minutos,
                        segundos = segundos,
                        uiState = uiState,
                        vm = vm,
                        stateInicio = stateInicio,
                        stateFin = stateFin,
                        onMostrarFechaInicio = { mostrarFechaInicio = true },
                        onMostrarFechaFin = {mostrarFechaFin = true},
                        onCancelar = {cancelar = true}
                    )
                }
                4 ->{
                    if (noTiempo) {
                        mostrarDialogo = false
                        mostrarErrorFecha = false
                        mostrarerrorFechaFin = false
                        mostrarFechaInicio = false
                        mostrarFechaFin = false
                        DialogError(
                            onDismiss = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                            },
                            onConfirm = {
                                nav.navigate("home")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }

                            },
                            titulo = "Se terminó el tiempo para solicitar el spot",
                            texto = "Por favor vuelve a intentarlo.",
                            icon = Icons.Default.Error
                        )
                    }

                    val minutos = tiempo / 60
                    val segundos = tiempo % 60

                    PagoScreen(
                        minutos= minutos,
                        segundos= segundos,
                        uiState = uiState,
                        vm = vm,
                        repoUser = repoUser,
                        onCancelar = { cancelar = true},
                        onPagar = {vm.crearRenta(usuario?.id)}
                    )

                }
                5 -> {
                    nav.navigate("home")
                }

            }
        }
    }

   if(mostrarFechaInicio){
       DatePickerDialog(
           onDismissRequest = {
               vm.seleccionarFechaInicio(null)
               mostrarFechaInicio = false
           },
           confirmButton = {
               TextButton(onClick = {
                   val selected = stateInicio.selectedDateMillis
                   val today = startOfTodayMillis()

                   if(selected == null)
                   {
                       mostrarFechaInicio = false
                       mostrarErrorFecha = true

                   }else if(selected < today){

                       mostrarFechaInicio = false
                       mostrarErrorFecha = true

                   }else {
                       mostrarFechaInicio = false
                       mostrarErrorFecha = false
                       vm.seleccionarFechaInicio(millisToMysqlDate(selected))
                   }
               }) {
                   Text(text = "ok" )
               }
           }
       ) {
           DatePicker(state = stateInicio)
       }
   }

    if (mostrarFechaFin) {
        DatePickerDialog(
            onDismissRequest = {
                vm.seleccionarFechaFin(null)
                mostrarFechaFin = false
                stateFin.selectedDateMillis = null

            },
            confirmButton = {
                TextButton(onClick = {
                    val selected = stateFin.selectedDateMillis
                    val today = startOfTodayMillis()
                    val inicioSelected = stateInicio.selectedDateMillis
                    when {
                        selected == null -> {
                            mostrarFechaFin = false
                            mostrarerrorFechaFin = true
                        }
                        selected < today -> {
                            mostrarFechaFin = false
                            mostrarerrorFechaFin = true
                        }
                        inicioSelected != null && selected < inicioSelected -> {
                            mostrarerrorFechaFin = true
                            mostrarerrorFechaFin = false
                        }
                        else -> {
                            mostrarFechaFin = false
                            mostrarerrorFechaFin = false
                            vm.seleccionarFechaFin(millisToMysqlDate(selected))
                        }
                    }
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = stateFin)
        }
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

    if (mostrarerrorFechaFin) {
        DialogError(
            titulo = "Fecha inválida",
            texto = "La fecha de fin no puede ser anterior a hoy.",
            onDismiss = {
                mostrarErrorFecha = false
                mostrarFechaFin = true
                        },
            onConfirm = {
                mostrarErrorFecha = false
                mostrarFechaFin = true
                        },
            icon = Icons.Default.Error
        )
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
    if(confirmar){
        DialogSuccess(
            onConfirm = {
                vm.crearRenta(usuario?.id)
            },
            onCancel = {confirmar = false},
            titulo = "Todos los datos son correctos?",
            texto = "antes de continuar asegurate de que todos los datos son correctos",
            icon = Icons.Default.Info,
            )
    }
}

fun millisToMysqlDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}
fun startOfTodayMillis(): Long {
    return LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
