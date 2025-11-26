package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun camposTarjeta(
){
    Column() {

        TextField(
            value = "4725 8628 8945 1234",
            label = "Número de tarjeta",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth()) {

            TextField(
                value = "08/30",
                label = "MM/YY",
                onValueChange = { },
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 60.dp)
                    .padding(end = 16.dp)
            )

            TextField(
                value = "456",
                label ="CVV",
                isPassword = true,
                onValueChange = { },
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 60.dp)
            )
        }
    }
}