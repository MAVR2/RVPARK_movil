package org.utl.rvpark_movil.register.ui

import RoleDropdown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.utils.components.PasswordTextField


@Preview(showBackground = true)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        onBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RegisterForm(
            Modifier.align(Alignment.Center),
            uiState = uiState,
            OnFirstNameChange = viewModel::updateFirstName,
            OnLastNameChange = viewModel::updateLastName,
            OnPassword1Change = viewModel::updatePassword1,
            OnPassword2Change = viewModel::updatePassword2,
            OnRolChange = viewModel::updateRol,
            onRegisterClick = {
                viewModel.register()
                if(uiState.isSuccess) onBack()
            }
        )
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier,
    uiState: RegsiterUiState,
    OnFirstNameChange: (String) -> Unit,
    OnLastNameChange: (String) -> Unit,
    OnPassword1Change: (String) -> Unit,
    OnPassword2Change: (String) -> Unit,
    OnRolChange: (Int) -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Crear cuenta",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.firstName,
            onValueChange = { OnFirstNameChange(it) },
            label = { Text("Nombre completo") }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.lastName,
            onValueChange = { OnLastNameChange(it) },
            label = { Text("Correo electrónico") }
        )

        Spacer(Modifier.height(12.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password1,
            label = { Text("Password") },
            onValueChange = { OnPassword1Change(it) }
        )

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password2,
            label = { Text("Confirm password") },
            onValueChange = { OnPassword2Change(it) }
        )

        Spacer(Modifier.height(24.dp))

        Spacer(Modifier.height(12.dp))

        RoleDropdown(
            selectedRole = uiState.rol,
            onRoleSelected = OnRolChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))



        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onRegisterClick() },
                enabled = !uiState.isLoanding
            ) {
                Text(text = if (uiState.isLoanding) "Loading..." else "Sign up")
            }
        }

        Spacer(Modifier.height(16.dp))

        uiState.error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = { onRegisterClick() }) {
                Text("Ya tengo cuenta")
            }
        }
    }
}
