package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.parking.data.model.Spot
import org.utl.rvpark_movil.parking.data.repository.ParkingRepository

data class ZonaUi(
    val nombre: String,
    val spots: List<Spot>
)

data class ParkingUiState(
    val zonas: List<ZonaUi> = emptyList(),
    val zonaSeleccionada: String? = null,
    val spotSeleccionado: Spot? = null,
    val fechaInicio: String? = null,
    val fechaFin: String? = null,
    val paso: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null
)


class ParkingViewModel(
    private val repo: ParkingRepository = ParkingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParkingUiState())
    val uiState = _uiState

    val zonasUi = MutableStateFlow<List<ZonaUi>>(emptyList())
    val zonas = MutableStateFlow<Map<String, List<Spot>>>(emptyMap())

    val fechaInicio = MutableStateFlow<String?>(null)
    val fechaFin = MutableStateFlow<String?>(null)

    val zonaSeleccionada = MutableStateFlow<String?>(null)
    val spotSeleccionado = MutableStateFlow<Spot?>(null)

    val paso = MutableStateFlow(1)   // 1 = zona, 2 = spot, 3 = pago

    fun seleccionarZona(nombre: String) {
        _uiState.update { it.copy(zonaSeleccionada = nombre, spotSeleccionado = null) }
    }

    fun SeleccionarSpot(spot: Spot){
        _uiState.update { it.copy(spotSeleccionado = spot) }
    }

    fun seleccionarFechaInicio(fecha: String?) {
        _uiState.update { it.copy(fechaInicio = fecha) }
    }

    fun seleccionarFechaFin(fecha: String?) {
        _uiState.update { it.copy(fechaFin = fecha) }
    }


    fun cargarZonas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val mapa = repo.obtenerZonas().data
                val zonasUi = mapa.map { (nombre, spots) ->
                    ZonaUi(nombre, spots)
                }

                _uiState.value = _uiState.value.copy(
                    zonas = zonasUi,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }




    fun siguientePaso() {
        paso.value = paso.value + 1
    }

    fun retrocederPaso() {
        paso.value -= 1
    }

    fun apartarSpot(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                repo.apartarSpot(id)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun cancelarSpot(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repo.cancelarSpot(id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

}
