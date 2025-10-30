package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.profile.ui.userUiState
import org.utl.rvpark_movil.utils.preferences.UserRepository



data class ParkingUiState(
    val listaLote: List<String>? = emptyList(),
    val listaPark: List<String>? = emptyList(),

    val lote: String? = "",
    val park: String? = "",

    //ui
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class ParkingViweModel: ViewModel(){

    private val _uiState = MutableStateFlow(ParkingUiState())
    val uiState: StateFlow<ParkingUiState> = _uiState.asStateFlow()

    fun updateLote(newLote: String){
        _uiState.value = _uiState.value.copy(lote = newLote)
    }
    fun updatePark(newPark: String){
        _uiState.value = _uiState.value.copy(park = newPark)
    }


    fun loadLotes(){
        _uiState.value = _uiState.value
    }

    fun loadParks(){

    }

    fun savePark(){

    }
}