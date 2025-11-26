package org.utl.rvpark_movil.contracts.ui

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.utl.rvpark_movil.parking.data.model.Pago
import org.utl.rvpark_movil.utils.saveContratoPdf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContratoDetailScreen(
    id_renta: Int,
    viewModel: ContractViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    LaunchedEffect(id_renta) {
        viewModel.loadRenta(id_renta)
    }

    val renta = uiState.renta

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle del contrato") })
        }
    ) { innerPadding ->

        when {
            uiState.loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${uiState.error}")
                }
            }

            renta != null -> {
                val qrData = "Contrato: ${renta.id_renta}\nUsuario: ${renta.id_usuario}"
                val qrBitmap = remember(renta.id_renta) { generateQrCode(qrData) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(innerPadding)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Top
                ) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Text(
                                text = "Contrato #${renta.id_renta}",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Divider()

                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                InfoRow("Email", renta.usuario?.nombre_usuario ?: "N/D")

                                InfoRow("Spot", renta.spot?.codigo_spot ?: renta.id_spot.toString())
                                InfoRow("Inicio", renta.fecha_inicio)
                                InfoRow("Fin", renta.fecha_fin ?: "N/A")

                                InfoRow("Total días", renta.total_dias?.toString() ?: "N/A")
                                InfoRow("Monto total", "$${renta.monto_total}")

                                InfoRow("Estatus pago", renta.estatus_pago)
                                InfoRow("Método de pago", renta.metodo_pago)

                                InfoRow("Observaciones", renta.observaciones ?: "N/A")
                            }

                            // PAGOS
                            if (!renta.pagos.isNullOrEmpty()) {
                                Divider()
                                Text(
                                    "Pagos",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    renta.pagos.forEach { pago ->
                                        PaymentCard(pago)
                                    }
                                }
                            }

                            // QR
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                Text("Código QR", style = MaterialTheme.typography.titleMedium)

                                Spacer(Modifier.height(10.dp))

                                Card(
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Image(
                                        bitmap = qrBitmap.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(20.dp)
                                            .size(220.dp)
                                    )
                                }
                            }
                        }
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        onClick = { saveContratoPdf(context, renta, qrBitmap) }
                    ) {
                        Text("Guardar PDF")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value ?: "N/A", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun PaymentCard(pago: Pago) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            InfoRow("Fecha pago", pago.fecha_pago)
            InfoRow("Monto", "$${pago.monto}")
            InfoRow("Periodo", pago.periodo)
            InfoRow("Método", pago.metodo_pago)
            InfoRow("Referencia", pago.referencia)
        }
    }
}




fun generateQrCode(data: String): Bitmap {
    val writer = QRCodeWriter()
    val matrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
    val bmp = createBitmap(512, 512, Bitmap.Config.RGB_565)
    for (x in 0 until 512) {
        for (y in 0 until 512) {
            bmp[x, y] = if (matrix[x, y]) Color.BLACK else Color.WHITE
        }
    }
    return bmp
}


