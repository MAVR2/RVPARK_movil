package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.utl.rvpark_movil.parking.ui.ZonaUi
import kotlin.collections.forEach

@Composable
fun ZonaDropdown(
    zonas: List<ZonaUi>,
    seleccion: ZonaUi?,
    onSelect: (ZonaUi) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .clickable { expanded = true }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Zona selecionada: ${seleccion?.nombre}" ?: "Selecciona una zona",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {

            zonas.forEach { zona ->
                DropdownMenuItem(
                    text = {
                        Text(
                            "${zona.nombre} • Espacios: ${zona.spots.size}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onSelect(zona)
                        expanded = false
                    }
                )
            }
        }
    }
}



