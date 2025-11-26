package org.utl.rvpark_movil.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.utl.rvpark_movil.home.data.model.Renta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarContrato(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<Renta>,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier
            .fillMaxWidth()
            .heightIn(max = 400.dp)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                        onSearch(textFieldState.text.toString())
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Buscar por ID de Contrato") }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp)
            ) {
                items(searchResults) { contrato ->
                    val displayText = "Contrato #${contrato.id_renta}"
                    ListItem(
                        headlineContent = { Text(displayText) },
                        trailingContent = { com.itextpdf.layout.element.Text(contrato.fecha_inicio) },
                        modifier = Modifier
                            .clickable {
                                navController.navigate("contratoDetalle/${contrato.id_renta}")
                                expanded = false
                            }
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

