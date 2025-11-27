package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.utl.rvpark_movil.home.data.model.Renta
import org.utl.rvpark_movil.parking.data.model.RentaCompleta


@Composable
fun ListaContratos(
    contratos: List<Renta>,
    navController: NavHostController
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        contratos.forEach { contrato ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Contrato #${contrato.id_renta}", style = MaterialTheme.typography.titleMedium)
                    Text("Espacio: ${contrato.spot?.codigo_spot ?: "N/A"}")
                    Text("Fecha inicio: ${contrato.fecha_inicio ?: ""}")
                    Text("Fecha fin: ${contrato.fecha_fin ?: ""}")

                    Button(
                        modifier = Modifier.padding(top = 8.dp),
                        onClick = {
                            navController.navigate("contratoDetalle/${contrato.id_renta}")
                        }
                    ) {
                        Text("Ver")
                    }
                }
            }
        }
    }
}


@Composable
fun ContratoCardCompact(
    contrato: RentaCompleta,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Ícono circular
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Contrato #${contrato.id_renta}",
                    style = MaterialTheme.typography.titleSmall
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = contrato.usuario?.nombre_usuario ?: "N/D",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = contrato.spot?.codigo_spot ?: contrato.id_spot.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = contrato.fecha_inicio,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            EstatusChipCompact(contrato.estatus_pago)
        }
    }
}

@Composable
fun EstatusChipCompact(estatus: String) {
    val (color, text) = when (estatus) {
        "Pagado" -> Color(0xFF4CAF50) to "Pagado"
        "Pendiente" -> Color(0xFFFFA000) to "Pendiente"
        "Cancelado" -> Color(0xFFD32F2F) to "Cancelado"
        else -> Color.Gray to estatus
    }

    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
