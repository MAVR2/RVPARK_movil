package org.utl.rvpark_movil.parking.ui

import DialogError
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.DialogSuccess
import org.utl.rvpark_movil.utils.components.SpotDropdown
import org.utl.rvpark_movil.utils.components.ZonaDropdown
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import org.utl.rvpark_movil.utils.components.PasoProgressBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(
    vm: ParkingViewModel = viewModel(),
    nav: NavHostController
) {

    val uiState by vm.uiState.collectAsState()

    var mostrarFechaInicio by remember { mutableStateOf(false) }
    var mostrarFechaFin by remember { mutableStateOf(false) }

    var mostrarErrorFecha by remember { mutableStateOf(false) }
    var mostrarerrorFechaFin by remember { mutableStateOf(false) }

    var cancelar by remember { mutableStateOf(false) }



    var tiempo by remember { mutableStateOf(300) }
    var noTiempo by remember { mutableStateOf(false) }

    var stateInicio = rememberDatePickerState()

    var stateFin = rememberDatePickerState(
        initialSelectedDateMillis = null
    )



    val paso by vm.paso.collectAsState()
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
                        zonas = uiState.zonas,
                        seleccion = uiState.zonas.firstOrNull { it.nombre == zonaSeleccionada },
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

                                cancelar = true
                                      },
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

                    val spots = uiState.zonas
                        .firstOrNull { it.nombre == zonaSeleccionada }
                        ?.spots ?: emptyList()


                    SpotDropdown(
                        spots = spots,
                        seleccion = uiState.spotSeleccionado,
                        onSelect = { vm.SeleccionarSpot(it) }
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
                                Log.d("debug", "revisar estado")
                                spotSeleccionado?.let { vm.apartarSpot(it.id_spot) }
                            },
                            enabled = spotSeleccionado != null
                        ) { Text("Siguiente") }
                    }

                }

                3 -> {
                    LaunchedEffect(Unit) {
                        while (tiempo > 0) {
                            delay(1000)
                            tiempo--
                        }
                        noTiempo = true
                    }



                    val minutos = tiempo / 60
                    val segundos = tiempo % 60

                    if (noTiempo) {
                        mostrarDialogo =false
                        mostrarErrorFecha =false
                        mostrarerrorFechaFin = false
                        mostrarFechaInicio  =false
                        mostrarFechaFin =false
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
                    Column(

                    ) {

                        PasoProgressBar(paso)

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = String.format("%02d:%02d", minutos, segundos),
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Solo unos detalles más..",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ){
                            val fechaHoy = Instant.ofEpochMilli(startOfTodayMillis())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ){
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp, 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,

                                ){
                                    Icon(imageVector = Icons.Default.Info, contentDescription = "")
                                }

                                Row(
                                    modifier = Modifier
                                        .padding(16.dp, 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,

                                    ){
                                    Text(
                                        text = "La fecha de inicio debe ser posterior a $fechaHoy.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp, 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Esto se debe a que el sistema confirma las reservaciones con un día de anticipación, garantizando la disponibilidad del espacio y la correcta programación de su contrato.",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                            }

                        }
                        Spacer(modifier= Modifier.size(16.dp))


                        Text("Fecha Inicio")
                        Column(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 14.dp)
                                    .clickable { mostrarFechaInicio = true }
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = stateInicio.selectedDateMillis?.let {
                                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().toString()
                                        } ?: "Seleccione la fecha de inicio",
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 12.dp)
                                    )

                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                            }

                        }

                        Spacer(modifier= Modifier.size(16.dp))

                        Text("Fecha Fin")
                        Column(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                        {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 14.dp)
                                    .clickable { mostrarFechaFin = true }
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = stateFin.selectedDateMillis?.let {
                                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().toString()
                                        } ?: "Seleccione la fecha de fin",
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 12.dp)
                                    )

                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                            }


                        }
                        if(uiState.fechaFin == null){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ){
                                Icon(
                                    Icons.Default.Info, tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier= Modifier.size(16.dp))
                                Text("Si dejas la fecha fin en blanco, se cobrará el resto del período")
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Button(
                                colors = ButtonColors(
                                    containerColor = Color.Red,
                                    contentColor =Color.White,
                                    disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                                    disabledContentColor = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier
                                    .padding(16.dp),
                                onClick ={

                                        cancelar = true
                                }){
                                Text(text="Cancelar",)
                            }
                            Button(
                                modifier = Modifier.padding(16.dp),
                                enabled = uiState.fechaInicio != null,
                                onClick ={
                                    vm.siguientePaso()
                                }){
                                Text(text="confirmar",)
                            }
                        }
                    }

                }
                4 -> {
                    if (paso == 4) {

                        LaunchedEffect(Unit) {
                            while (tiempo > 0) {
                                delay(1000)
                                tiempo--
                            }
                            noTiempo = true
                        }



                        val minutos = tiempo / 60
                        val segundos = tiempo % 60

                        if (noTiempo) {
                            mostrarDialogo =false
                            mostrarErrorFecha =false
                            mostrarerrorFechaFin = false
                            mostrarFechaInicio  =false
                            mostrarFechaFin =false
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


                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            PasoProgressBar(paso)


                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .padding(bottom = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = String.format("%02d:%02d", minutos, segundos),
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally),
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

    if (cancelar) {
        DialogSuccess(
            titulo = "Esta seguro que desea cancelar?",
            texto = "se cancelara el proceso de contrato del spot",
            onCancel = {
                cancelar = false
            },
            onConfirm = {
                Log.d("debug","se cancelo y se fue a home")
                spotSeleccionado?.let {
                    vm.cancelarSpot(it.id_spot)
                }
                nav.navigate("home")
                cancelar = false

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
