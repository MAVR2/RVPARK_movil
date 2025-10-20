package org.utl.rvpark_movil.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.home.data.Contrato
import org.utl.rvpark_movil.home.data.getContratos
import org.utl.rvpark_movil.utils.preferences.UserRepository


data class homeUiState(
    val contratos: List<Contrato>? = null

)
class HomeViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(homeUiState())
    val uiState: StateFlow<homeUiState> = _uiState.asStateFlow()

    fun loadContratos(userRepository: UserRepository) {
        viewModelScope.launch {
            val contratos = getContratos(userRepository)
            _uiState.value = _uiState.value.copy(contratos = contratos)
        }
    }
}