package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.home.data.Contrato
import org.utl.rvpark_movil.home.data.hoy
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.max


@Composable
fun ListaContratos(contratos: List<Contrato>?) {
    if (contratos.isNullOrEmpty()) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(contratos) { contrato ->
                ContratoCard(contrato = contrato)
            }
        }
    }
}

@Composable
fun ContratoCard(contrato: Contrato) {
    val fechaInicio = LocalDate.parse(contrato.fecha_inicio)
    val fechaFin = LocalDate.parse(contrato.fecha_fin)
    val diasTotales = ChronoUnit.DAYS.between(fechaInicio, fechaFin).toFloat()
    val diasRestantes = max(ChronoUnit.DAYS.between(hoy, fechaFin).toFloat(), 0f)
    val progreso = if (diasTotales > 0) 1f - (diasRestantes / diasTotales) else 1f


    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Contrato #${contrato.id_renta}")
            Text(text = "Cliente: ${contrato.id_cliente}")
            Text(text = "Inicio: ${contrato.fecha_inicio}")
            Text(text = "Fin: ${contrato.fecha_fin}")
            Text(text = "Monto: $${contrato.monto_total}")
            Text(text = "Estatus: ${contrato.estatus_pago}")
            Text(text = "progreso: ${progreso}")

            Spacer(modifier = Modifier.height(8.dp))

            CircularProgressIndicator(
                progress = { progreso.coerceIn(0f, 1f)  },
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Días restantes: ${diasRestantes.toInt()}",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

