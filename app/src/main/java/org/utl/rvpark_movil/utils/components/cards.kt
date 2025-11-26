package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.home.data.model.Renta
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.max
import androidx.navigation.NavHostController



@Composable
fun ListaContratos(
    rentas: List<Renta>?,
    navHostController: NavHostController
) {
    if (rentas.isNullOrEmpty()) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(120.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Aún no se ha realizado ningún contrato")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            rentas.forEach { contrato ->
                ContratoCard(
                    renta = contrato,
                    navController = navHostController
                    )
            }
        }
    }
}

@Composable
fun ContratoCard(
    renta: Renta,
    navController: NavHostController
) {
    val fechaInicio = LocalDate.parse(renta.fecha_inicio)
    val fechaFin = LocalDate.parse(renta.fecha_fin)
    val diasTotales = ChronoUnit.DAYS.between(fechaInicio, fechaFin).toFloat()


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight()
            .clickable {
                navController.navigate("contratoDetalle/${renta.id_renta}")
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth()){
            Spacer(Modifier.height(16.dp))
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Contrato #${renta.id_renta}")
            Text(text = "Inicio: ${renta.fecha_inicio}")
            Text(text = "Fin: ${renta.fecha_fin}")
            Text(text = "Monto: $${renta.monto_total}")
            Text(text = "Estatus: ${renta.estatus_pago}")

            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}

