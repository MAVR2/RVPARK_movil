package org.utl.rvpark_movil.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.ui.theme.orange600
import org.utl.rvpark_movil.utils.components.PasswordTextField
import org.utl.rvpark_movil.utils.preferences.UserRepository


@Preview(showBackground = true)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit
){

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = UserRepository(context)

    if (uiState.isSuccess) {
        onLoginSuccess()
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {

        Login(
            Modifier.align(Alignment.Center),
            uiState = uiState,
            onEmailChange = viewModel::updateEmail,
            onPassChange = viewModel::updatePassword,
            onLoginClick = {
                viewModel.login(userRepository)
                if (uiState.isSuccess) onLoginSuccess()
            },
            onRegister = onRegister
        )
    }
}

@Composable
fun Login(
    modifier: Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onLoginClick: () ->Unit,
    onRegister: () -> Unit
) {

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            text = "RVPARK movil",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = { Text(text = "Email") },
            placeholder = {
                Text(
                    text = "Correo electrónico",
                    style = TextStyle(color = orange600)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = uiState.password,
            onValueChange = onPassChange,
            label = {Text(text = "Password")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = { onLoginClick() },
            enabled = !uiState.isLoanding
        ) {
            Text(text = if (uiState.isLoanding) "Loanding..." else "Log in")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error != null) {
            Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
        }

        TextButton(
            onClick = {onRegister()}
        ) {
            Text(
                text = "Register",
                color = orange600
            )
        }
    }
}
