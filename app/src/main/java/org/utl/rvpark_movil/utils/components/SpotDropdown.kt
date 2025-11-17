package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.utl.rvpark_movil.parking.data.model.Spot
import kotlin.collections.forEach

@Composable
fun SpotDropdown(
    spots: List<Spot>,
    seleccion: Spot?,
    onSelect: (Spot) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(seleccion?.codigo_spot ?: "Seleccionar spot")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            spots.forEach { spot ->
                DropdownMenuItem(
                    text = { Text(spot.codigo_spot) },
                    onClick = {
                        onSelect(spot)
                        expanded = false
                    }
                )
            }
        }
    }
}