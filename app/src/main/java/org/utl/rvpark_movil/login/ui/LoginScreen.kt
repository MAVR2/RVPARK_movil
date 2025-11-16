package org.utl.rvpark_movil.login.ui

import DialogError
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.R
import org.utl.rvpark_movil.utils.components.GlassCard
import org.utl.rvpark_movil.utils.components.LoadingDialog
import org.utl.rvpark_movil.utils.components.TextField
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = UserRepository(context)

    val showDialog = uiState.error != null

    if (uiState.isSuccess) onLoginSuccess()


    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.login_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize().blur(10.dp),
            contentScale = ContentScale.Crop
        )

        // Card translúcida NÍTIDA encima
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
                .fillMaxWidth(0.9f)
        ) {
            GlassCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Icon(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(250.dp),
                        tint = Color.White
                    )

                    Spacer(Modifier.height(24.dp))

                    TextField(
                        value = uiState.email,
                        onValueChange = viewModel::updateEmail,
                        label = "Correo electrónico",
                        icon = Icons.Default.Email,
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = Color.White,
                        labelColor = Color.White,
                        iconColor = Color.White,
                        textColor = Color.White

                    )

                    Spacer(Modifier.height(12.dp))

                    TextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        label = "Contraseña",
                        isPassword = true,
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = Color.White,
                        labelColor = Color.White,
                        iconColor = Color.White,
                        textColor = Color.White
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.login(userRepository) },
                        modifier = Modifier.fillMaxWidth(0.7f),
                        enabled = !uiState.isLoading
                    ) {

                        Text("Iniciar sesion")
                        Spacer(modifier = Modifier.size(10.dp))
                        Icon(
                            Icons.Default.Login,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    TextButton(onClick = onRegister) {
                        Text(
                            text="Registrarse",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    if(uiState.isLoading){
        LoadingDialog(message = "Iniciando sesion...")
    }

    if (showDialog) {
        DialogError(
            onDismiss = { viewModel.clearError()},
            onConfirm = { viewModel.clearError()},
            titulo = "Ups, algo salió mal",
            texto = uiState.error ?: "Ocurrió un problema.",
            icon = Icons.Default.Error
        )
    }

}
