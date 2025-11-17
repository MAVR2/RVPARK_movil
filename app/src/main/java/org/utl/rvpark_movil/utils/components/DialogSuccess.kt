package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle

@Composable
fun DialogSuccess(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    titulo: String,
    texto: String,
    icon: ImageVector = Icons.Default.CheckCircle
) {
    AlertDialog(
        onDismissRequest = { onCancel() },

        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color(0xFF4CAF50)
            )
        },

        title = { Text(titulo) },

        text = { Text(texto) },

        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Aceptar")
            }
        },

        dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text("cancelar")
            }
        }
    )
}

