package org.utl.rvpark_movil.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun EditarUserScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf(uiState.name) }
    var lastName by remember { mutableStateOf(uiState.lastName) }
    var email by remember { mutableStateOf(uiState.email) }
    var phone by remember { mutableStateOf(uiState.phone) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Editar Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellidos") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") })
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.updateName(name)
                viewModel.updateLastName(lastName)
                viewModel.updateEmail(email)
                viewModel.updatePhone(phone)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}
