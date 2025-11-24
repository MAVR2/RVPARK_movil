package org.utl.rvpark_movil.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.utl.rvpark_movil.utils.preferences.UserRepository
import org.utl.rvpark_movil.utils.components.HeroCarousel



@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val userRepository = remember { UserRepository(context) }
    val nombre by userRepository.nombre.collectAsState(initial = "")

    LaunchedEffect(nombre) {
        viewModel.loadContratos(userRepository)
        viewModel.setNombre(nombre ?: "")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("chatBot") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Message, contentDescription = "Chatbot", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Hola $nombre!",
                style = MaterialTheme.typography.headlineMedium
            )


            Spacer(Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp, 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Bienvenido!", style = MaterialTheme.typography.titleMedium)
                        Text("Mira los eventos que estamos preparando en RVPARK", style = MaterialTheme.typography.bodyMedium)
                    }
                    Icon(Icons.Default.Celebration, contentDescription = null, modifier = Modifier.size(48.dp))
                }
                Row(
                    modifier = Modifier.padding(8.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeroCarousel(navHostController = navController)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Contratos de renta", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if(uiState.contratos.isEmpty()){
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Help, contentDescription = "",
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.outline
                                )

                            }
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                Text(text = "Parece que aun no tienes un contrato de renta.",
                                    color = MaterialTheme.colorScheme.outline)

                            }
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically)
                            {
                                Button(
                                    onClick = {navController.navigate("nuevoContrato")}
                                ) {
                                    Icon(Icons.Default.Add,
                                        contentDescription = "")
                                    Text("Solicitar un espacio")
                                }
                            }
                        }
                    }
                }else{
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    uiState.contratos.forEach { contrato ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text= "${contrato.id_renta}", style = MaterialTheme.typography.titleMedium)
                                Text("lugar del espacio: ${contrato.id_spot}%", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
