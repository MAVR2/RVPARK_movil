package org.utl.rvpark_movil.profile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.utl.rvpark_movil.utils.components.TextField
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Composable
fun EditarUserScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val userRepository = UserRepository(context)


    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Editar usuario",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = uiState.name,
            label = "Nombre",
            onValueChange = { viewModel.updateName(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))



        TextField(
            value = uiState.email,
            label = "Correo",
            onValueChange = { viewModel.updateEmail(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.phone,
            label = "Teléfono",
            onValueChange = { viewModel.updatePhone(it) },
            keyboardType = KeyboardType.Phone,
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                viewModel.savePersona(
                    userRepository =userRepository,
                    onDone = {"Actualizado"}
                )

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Guardar cambios")
        }
    }
}
