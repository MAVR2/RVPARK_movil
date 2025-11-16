package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingDialog(
    message: String = "Cargando..."
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {},
        title = {},
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingDialogPreview() {
    LoadingDialog()
}
