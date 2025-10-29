package org.utl.rvpark_movil.contracts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.home.ui.HomeViewModel
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.utl.rvpark_movil.home.data.Contrato
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Image
import com.itextpdf.io.image.ImageDataFactory
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider

@Composable
fun ContratoDetailScreen(
    contratoId: Int,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val contrato = uiState.contratos.find { it.id_renta == contratoId } ?: Contrato(
        id_renta = contratoId,
        id_cliente = 999,
        id_spot = 1,
        fecha_inicio = "2025-10-01",
        fecha_fin = "2025-10-10",
        total_dias = 9,
        monto_total = 1234.56,
        estatus_pago = "Pagado",
        metodo_pago = "Efectivo",
        observaciones = "Contrato generado de forma temporal"
    )

    val qrData = "Contrato #${contrato.id_renta}\nTitular: Cliente ${contrato.id_cliente}"
    val qrBitmap = remember { generateQrCode(qrData) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Contrato #${contrato.id_renta}", style = MaterialTheme.typography.titleLarge)
        Text("Cliente: ${contrato.id_cliente}")
        Text("Inicio: ${contrato.fecha_inicio}")
        Text("Fin: ${contrato.fecha_fin}")
        Text("Monto: $${contrato.monto_total}")
        Text("Estatus: ${contrato.estatus_pago}")
        Text("Método de pago: ${contrato.metodo_pago}")
        Text("Observaciones: ${contrato.observaciones}")

        Spacer(Modifier.height(20.dp))

        Image(
            bitmap = qrBitmap.asImageBitmap(),
            contentDescription = "Código QR del contrato",
            modifier = Modifier.size(250.dp)
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = { saveContratoPdf(context, contrato, qrBitmap) }) {
            Text("Guardar PDF")
        }
    }
}

fun generateQrCode(data: String): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
    val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
    for (x in 0 until 512) {
        for (y in 0 until 512) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }
    return bitmap
}

fun saveContratoPdf(context: Context, contrato: Contrato, qrBitmap: Bitmap) {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Contrato_${contrato.id_renta}.pdf")

    val pdfWriter = PdfWriter(FileOutputStream(file))
    val pdfDoc = PdfDocument(pdfWriter)
    val document = Document(pdfDoc)

    document.add(Paragraph("Contrato #${contrato.id_renta}"))
    document.add(Paragraph("Cliente: ${contrato.id_cliente}"))
    document.add(Paragraph("Inicio: ${contrato.fecha_inicio}"))
    document.add(Paragraph("Fin: ${contrato.fecha_fin}"))
    document.add(Paragraph("Monto: $${contrato.monto_total}"))
    document.add(Paragraph("Estatus: ${contrato.estatus_pago}"))
    document.add(Paragraph("Método de pago: ${contrato.metodo_pago}"))
    document.add(Paragraph("Observaciones: ${contrato.observaciones}"))

    // Convertir el QR en imagen PDF
    val stream = ByteArrayOutputStream()
    qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val imageData = ImageDataFactory.create(stream.toByteArray())
    val image = Image(imageData)
    image.scaleToFit(200f, 200f)
    document.add(image)

    document.close()

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, "application/pdf")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(intent)

}
