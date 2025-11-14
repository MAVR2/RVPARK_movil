package org.utl.rvpark_movil.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EditarPagoScreen(navController: NavHostController) {
    var nombreTitular by remember { mutableStateOf("") }
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaExp by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var paypal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Editar Métodos de Pago", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Tarjeta de crédito / débito", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombreTitular,
            onValueChange = { nombreTitular = it },
            label = { Text("Nombre del titular") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = numeroTarjeta,
            onValueChange = { if (it.length <= 16) numeroTarjeta = it.filter { c -> c.isDigit() } },
            label = { Text("Número de tarjeta") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = fechaExp,
                onValueChange = { if (it.length <= 5) fechaExp = it },
                label = { Text("MM/AA") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = cvv,
                onValueChange = { if (it.length <= 3) cvv = it.filter { c -> c.isDigit() } },
                label = { Text("CVV") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Cuenta PayPal (opcional)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = paypal,
            onValueChange = { paypal = it },
            label = { Text("Correo de PayPal") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}
