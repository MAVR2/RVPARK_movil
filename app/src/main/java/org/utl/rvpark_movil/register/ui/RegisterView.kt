package org.utl.rvpark_movil.register.ui

import DialogError
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.DialogSuccess
import org.utl.rvpark_movil.utils.components.GlassCard
import org.utl.rvpark_movil.utils.components.TextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.login_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize().blur(10.dp),
            contentScale = ContentScale.Crop
        )

        RegisterForm(
            uiState = uiState,
            OnFirstNameChange = viewModel::updateFirstName,
            OnLastNameChange = viewModel::updateLastName,
            OnPhoneChange = viewModel::updatePhone,
            OnEmailChange = viewModel::updateEmail,
            OnPassword1Change = viewModel::updatePassword1,
            OnPassword2Change = viewModel::updatePassword2,
            onRegisterClick = viewModel::register,
            onClearError =  viewModel::clearError,
            onBack = onBack
        )
    }
}

@Composable
fun RegisterForm(
    uiState: RegsiterUiState,
    OnFirstNameChange: (String) -> Unit,
    OnLastNameChange: (String) -> Unit,
    OnPhoneChange: (String) -> Unit,
    OnEmailChange: (String) -> Unit,
    OnPassword1Change: (String) -> Unit,
    OnPassword2Change: (String) -> Unit,
    onClearError: () -> Unit,
    onRegisterClick: () -> Unit,
    onBack: () -> Unit,
) {


    val showDialog = uiState.error != null


    GlassCard(
        modifier = Modifier.fillMaxWidth().padding(16.dp).padding(0.dp,25.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                tint = Color.White
            )

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White

            )

            Text(
                text = "Regístrate para continuar",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(Modifier.height(16.dp))
            Divider()

            Spacer(Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.firstName,
                onValueChange = OnFirstNameChange,
                label = "Nombres",
                icon = Icons.Default.Person,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.lastName,
                onValueChange = OnLastNameChange,
                label = "Apellidos",
                icon = Icons.Default.Person,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.phone,
                onValueChange = OnPhoneChange,
                label = "Teléfono",
                icon = Icons.Default.Phone,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = OnEmailChange,
                label = "Correo electrónico",
                icon = Icons.Default.Email,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password1,
                onValueChange = OnPassword1Change,
                label = "Contraseña",
                icon = Icons.Default.Lock,
                isPassword = true,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password2,
                onValueChange = OnPassword2Change,
                label = "Confirmar contraseña",
                icon = Icons.Default.Lock,
                isPassword = true,
                borderColor = Color.White,
                labelColor = Color.White,
                iconColor = Color.White,
                textColor = Color.White
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onRegisterClick,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(text = if (uiState.isLoading) "Cargando..." else "Registrarse")
            }

            Spacer(Modifier.height(10.dp))

            TextButton(onClick = onBack) {
                Text(text ="Ya tengo cuenta", color = Color.White)
            }
        }
    }

    if(uiState.isSuccess){
        DialogSuccess(
            onConfirm = {onBack()},
            titulo = "Exito!",
            texto = "Registro exitoso, por favor inicia sesion."
        )
    }

    if (showDialog) {
        DialogError(
            onDismiss = { onClearError() },
            onConfirm = { onClearError() },
            titulo = "Ups, algo salió mal",
            texto = uiState.error ?: "Ocurrió un problema.",
            icon = Icons.Default.Error
        )
    }

}
