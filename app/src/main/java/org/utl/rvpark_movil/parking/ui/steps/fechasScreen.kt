package org.utl.rvpark_movil.parking.ui.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.home.ui.homeUiState
import org.utl.rvpark_movil.parking.ui.ParkingUiState
import org.utl.rvpark_movil.parking.ui.ParkingViewModel
import org.utl.rvpark_movil.parking.ui.startOfTodayMillis
import org.utl.rvpark_movil.utils.components.PasoProgressBar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun fechasScreen(
    minutos: Int,
    segundos: Int,
    uiState: ParkingUiState,
    vm: ParkingViewModel,
    stateInicio: DatePickerState,
    stateFin: DatePickerState,

    onMostrarFechaInicio: () -> Unit,
    onMostrarFechaFin: () -> Unit,
    onCancelar: () -> Unit

){
    Column(

    ) {

        PasoProgressBar(uiState.paso)

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
                    .clickable { onMostrarFechaInicio() }
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
                    .clickable {
                        onMostrarFechaFin()
                    }
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
                    onCancelar()
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