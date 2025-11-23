package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.parking.data.model.CalculoPago
import org.utl.rvpark_movil.parking.data.model.Spot
import org.utl.rvpark_movil.parking.data.model.rentaCalRequest
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

    val rentaCalculada: CalculoPago? =null,
    val totalDias: Int? = null,

    val paso: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null
)


class ParkingViewModel(
    private val repo: ParkingRepository = ParkingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParkingUiState())
    val uiState = _uiState

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

    fun obtenerCalculoRenta() {
        val inicio = uiState.value.fechaInicio
        val fin = uiState.value.fechaFin

        if (inicio == null) {
            _uiState.update { it.copy(error = "Selecciona una fecha de inicio") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val req = rentaCalRequest(
                    fecha_inicio = inicio,
                    fecha_fin = fin
                )

                val resp = repo.calcularRenta(req)

                if (!resp.success) {
                    _uiState.update { it.copy(isLoading = false, error = resp.message) }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rentaCalculada = resp.calculoPago,
                        totalDias = resp.data?.total_dias
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }




    fun cargarZonas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val mapa = repo.obtenerZonas().data
                val zonasUi = mapa.map { (nombre, spots) ->
                    ZonaUi(nombre, spots)
                }

                _uiState.update { it.copy(zonas = zonasUi, isLoading = false) }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }



    fun siguientePaso() {
        _uiState.update {
            it.copy(paso = it.paso + 1)
        }
    }

    fun retrocederPaso() {
        _uiState.update {
            it.copy(paso = it.paso - 1)
        }
    }

    fun apartarSpot(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repo.apartarSpot(id)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun cancelarSpot(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repo.cancelarSpot(id)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

