package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.parking.data.model.CalculoPago
import org.utl.rvpark_movil.parking.data.model.CrearRentaRequest
import org.utl.rvpark_movil.parking.data.model.Spot
import org.utl.rvpark_movil.parking.data.model.rentaCalRequest
import org.utl.rvpark_movil.parking.data.repository.ParkingRepository
import org.utl.rvpark_movil.utils.costoUmitario
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    val montoTotal: Double? = null,
    val totalDias: Int? = null,

    val paso: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null,
    val rentaCalculada: CalculoPago? = null
)

class ParkingViewModel(
    private val repo: ParkingRepository = ParkingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParkingUiState())
    val uiState = _uiState

    fun seleccionarZona(nombre: String) {
        _uiState.update { it.copy(zonaSeleccionada = nombre) }
    }

    fun seleccionarSpot(spot: Spot) {
        _uiState.update { it.copy(spotSeleccionado = spot) }
    }

    fun seleccionarFechaInicio(fecha: String?) {
        _uiState.update { it.copy(fechaInicio = fecha) }
    }

    fun seleccionarFechaFin(fecha: String?) {
        _uiState.update { it.copy(fechaFin = fecha) }
    }

    private fun calcularDuracionDias(): Int {
        val inicio = uiState.value.fechaInicio ?: return 1
        val fin = uiState.value.fechaFin ?: inicio

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val f1 = LocalDate.parse(inicio, formatter)
        val f2 = LocalDate.parse(fin, formatter)

        val dias = ChronoUnit.DAYS.between(f1, f2).toInt()
        return if (dias <= 0) 1 else dias
    }

    fun apartarSpot(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                repo.apartarSpot(id)

                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun crearRenta(id_usuario: String?) {
        val spot = uiState.value.spotSeleccionado ?: run {
            _uiState.update { it.copy(error = "Selecciona un lugar") }
            return
        }

        val inicio = uiState.value.fechaInicio ?: run {
            _uiState.update { it.copy(error = "Selecciona la fecha de inicio") }
            return
        }

        val fin = uiState.value.fechaFin
        val duracionDias = calcularDuracionDias()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val req = CrearRentaRequest(
                    id_usuario = id_usuario.toString(),
                    id_spot = spot.id_spot.toString(),
                    fecha_inicio = inicio,
                    fecha_fin = fin,
                    tipo_renta = "personalizado",
                    tarifa_unitaria = costoUmitario,
                    duracion = duracionDias,
                    observaciones = "Renta desde app móvil"
                )

                val resp = repo.crearRenta(req)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        montoTotal = resp.info.monto_total,
                        totalDias = resp.info.total_dias,
                        paso = 5
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun ultimoDiaDelMes(fecha: String?): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(fecha, formatter)
        val ultimoDia = date.withDayOfMonth(date.lengthOfMonth())
        return ultimoDia.format(formatter)
    }

    fun cargarZonas() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

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

    fun siguientePaso() {
        _uiState.update { it.copy(paso = it.paso + 1) }
    }

    fun retrocederPaso() {
        _uiState.update { it.copy(paso = it.paso - 1) }
    }
}
