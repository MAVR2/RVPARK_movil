import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@Preview
fun DialogError(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    titulo: String,
    texto: String,
    icon: ImageVector
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },

        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color.Red

            )
        },

        title = {
            Text(titulo)
        },

        text = {
            Text(texto)
        },

        confirmButton = {

            TextButton(onClick = { onDismiss() }) {
            Text("Aceptar")
        }},

        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
