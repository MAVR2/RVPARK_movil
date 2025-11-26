package org.utl.rvpark_movil.parking.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.parking.ui.ParkingUiState
import org.utl.rvpark_movil.parking.ui.ParkingViewModel
import org.utl.rvpark_movil.utils.components.PasoProgressBar
import org.utl.rvpark_movil.utils.components.camposTarjeta
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Composable
fun PagoScreen(
    minutos: Int,
    segundos: Int,
    uiState: ParkingUiState,
    vm: ParkingViewModel,
    repoUser: UserRepository,
    onCancelar: () -> Unit,
    onPagar: () -> Unit
) {

    val usuario by repoUser.user2.collectAsState(initial = null)
    val scrollState = rememberScrollState()

    if (uiState.fechaFin == null) {
        vm.seleccionarFechaFin(vm.ultimoDiaDelMes(uiState.fechaInicio))
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {

        PasoProgressBar(uiState.paso)

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = String.format("%02d:%02d", minutos, segundos),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Confirmación",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            InfoItem("Titular", usuario?.name)
            InfoItem("Teléfono", usuario?.phone)
            InfoItem("Correo electrónico", usuario?.email)
            InfoItem("Zona seleccionada", uiState.zonaSeleccionada)
            InfoItem("Código del spot", uiState.spotSeleccionado?.codigo_spot)
            InfoItem("Fecha de inicio", uiState.fechaInicio)
            InfoItem("Fecha de fin", uiState.fechaFin)

            Text(
                text = "Detalles",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )

            InfoItem("Costo de la renta", uiState.rentaCalculada?.monto)
            InfoItem("Días restantes", uiState.rentaCalculada?.diasRestantes)
            InfoItem("Periodo", uiState.rentaCalculada?.periodo)
            InfoItem(
                "Día del fin del contrato",
                uiState.rentaCalculada?.ultimoDiaMes
            )

            Text(
                text = "Datos de la tarjeta",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            camposTarjeta()
            Spacer(modifier = Modifier.size(16.dp))

            Row(modifier =Modifier.fillMaxWidth())
            {
                Button(
                    colors = ButtonColors(
                        Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.errorContainer,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    onClick = {
                        onCancelar()
                    }

                ) {
                    Text("Cancelar")
                }

                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        onPagar()
                    }

                ) {
                    Text("Terminar pago")
                }
            }
        }
    }
}
@Composable
fun InfoItem(titulo: String, valor: Any?) {
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "$titulo: ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = valor?.toString() ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}