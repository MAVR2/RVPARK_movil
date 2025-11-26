package org.utl.rvpark_movil.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.content.FileProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Image
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import org.utl.rvpark_movil.parking.data.model.RentaCompleta
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun saveContratoPdf(context: Context, renta: RentaCompleta, qrBitmap: Bitmap) {

    val file = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
        "Contrato_${renta.id_renta}.pdf"
    )

    val writer = PdfWriter(FileOutputStream(file))
    val pdfDoc = PdfDocument(writer)
    val document = Document(pdfDoc)

    document.setMargins(40f, 40f, 40f, 40f)

    // Título
    document.add(
        Paragraph("Contrato de Renta ")
            .setFontSize(22f)
            .setBold()
            .setMarginBottom(20f)
    )

    document.add(
        Paragraph("Contrato #${renta.id_renta}")
            .setFontSize(16f)
            .setBold()
            .setMarginBottom(15f)
    )

    // Sección
    document.add(
        Paragraph("Datos Generales")
            .setBold()
            .setFontSize(14f)
            .setMarginBottom(10f)
    )

    // Tabla
    val table = Table(floatArrayOf(150f, 300f))
        .setMarginBottom(20f)

    fun row(label: String, value: String?) {
        table.addCell(Paragraph(label).setBold())
        table.addCell(Paragraph(value ?: "N/A"))
    }

    row("Usuario", renta.usuario?.nombre_usuario ?: renta.id_usuario.toString())
    row("Spot", renta.spot?.codigo_spot ?: renta.id_spot.toString())
    row("Inicio", renta.fecha_inicio)
    row("Fin", renta.fecha_fin ?: "N/A")
    row("Total días", renta.total_dias?.toString())
    row("Monto total", "$${renta.monto_total}")
    row("Estatus pago", renta.estatus_pago)
    row("Método de pago", renta.metodo_pago)
    row("Observaciones", renta.observaciones ?: "N/A")

    document.add(table)

    // Sección QR
    document.add(
        Paragraph("Código QR")
            .setBold()
            .setFontSize(20f)
            .setMarginBottom(10f)
    )

    val stream = ByteArrayOutputStream()
    qrBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val imageData = ImageDataFactory.create(stream.toByteArray())
    val image = Image(imageData)

    image.scaleToFit(200f, 200f)
    image.setHorizontalAlignment(HorizontalAlignment.CENTER)
    image.setMarginBottom(20f)

    document.add(image)

    // Pie de página
    document.add(
        Paragraph("Documento generado automáticamente por RV Park App")
            .setFontSize(10f)
            .setItalic()
            .setMarginTop(30f)
            .setHorizontalAlignment(HorizontalAlignment.CENTER)
    )

    document.close()

    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(intent)
}
