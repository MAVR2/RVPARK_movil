package org.utl.rvpark_movil.utils.components

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*


@Composable
fun DateDropdown(
    label: String,
    dateMillis: Long?,
    onSelect: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {

        // Caja principal (como tu SpotDropdown)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .clickable { expanded = true }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = dateMillis?.let { formatter.format(Date(it)) } ?: label,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Dropdown (aquí metemos el datepicker)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .height(350.dp)   // tamaño fijo para evitar overflow
                .width(IntrinsicSize.Min)
        ) {

            // DatePicker adentro
            AndroidView(
                factory = { context ->
                    DatePicker(context).apply {
                        dateMillis?.let { updateDate(
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).year,
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).monthValue - 1,
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).dayOfMonth
                        ) }
                    }
                },
                update = {}
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenuItem(
                text = { Text("Aceptar") },
                onClick = {
                    // recuperar fecha seleccionada
                    expanded = false
                    val pickerDate = Calendar.getInstance().apply {
                        timeInMillis = System.currentTimeMillis()
                    }
                    onSelect(
                        pickerDate.timeInMillis
                    )
                }
            )
        }
    }
}
