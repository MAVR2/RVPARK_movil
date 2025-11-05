package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ParkingUiState(
    val listaZona: List<String> = emptyList(),
    val listaPark: List<String> = emptyList(),
    val lote: String = "",
    val park: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class ParkingViweModel : ViewModel() {

    private val _uiState = MutableStateFlow(ParkingUiState())
    val uiState: StateFlow<ParkingUiState> = _uiState.asStateFlow()

    fun loadZonas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(500)
            _uiState.value = _uiState.value.copy(
                listaZona = listOf("Zona Norte", "Zona Sur", "Zona Este", "Zona Oeste"),
                isLoading = false
            )
        }
    }

    fun loadParks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(500)
            val parques = when (_uiState.value.lote) {
                "Zona Norte" -> listOf("N1", "N2", "N3", "N4", "N5", "N6", "N7","N1", "N2", "N3", "N4", "N5", "N6", "N7")
                "Zona Sur" -> listOf("S1", "S2", "S3")
                "Zona Este" -> listOf("E1", "E2", "E3")
                "Zona Oeste" -> listOf("O1", "O2", "O3")
                else -> emptyList()
            }
            _uiState.value = _uiState.value.copy(
                listaPark = parques,
                isLoading = false
            )
        }
    }

    fun updateZona(newLote: String) {
        _uiState.value = _uiState.value.copy(
            lote = newLote,
            park = "",
            listaPark = emptyList(),
            isSuccess = false)
        loadParks()
    }

    fun updatePark(newPark: String) {
        _uiState.value = _uiState.value.copy(park = newPark)
    }

    fun savePark() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(800)
            _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
        }
    }
}
