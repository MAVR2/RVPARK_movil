package org.utl.rvpark_movil.profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.rvpark_movil.utils.preferences.UserRepository

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onEditarUser: () -> Unit,
    onEditarPago: () -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = UserRepository(context)


    if (uiState.isSuccess) {
        onEditarUser()
    }

    Box(
        Modifier.fillMaxSize()
            .padding(16.dp),

    ){
        Profile(
            uiState = uiState,
            onEditarUser = viewModel::editarUser,
            onEditarPago = viewModel::editarPago
        )
    }
}

@Composable
fun Profile(
    uiState: userUiState,
    onEditarUser: () -> Unit,
    onEditarPago: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Text(
            text="User profile",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
        )

    }
}