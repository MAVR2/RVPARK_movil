package org.utl.rvpark_movil.parking.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.parking.data.model.Spot
import org.utl.rvpark_movil.parking.data.repository.ParkingRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class ZonaUi(
    val nombre: String,
    val spots: List<Spot>
)

class ParkingViewModel(
    private val repo: ParkingRepository = ParkingRepository()
) : ViewModel() {

    val zonasUi = MutableStateFlow<List<ZonaUi>>(emptyList())

    val zonas = MutableStateFlow<Map<String, List<Spot>>>(emptyMap())

    val fechaInicio = MutableStateFlow<String?>(null)
    val fechaFin = MutableStateFlow<String?>(null)



    val zonaSeleccionada = MutableStateFlow<String?>(null)
    val spotSeleccionado = MutableStateFlow<Spot?>(null)

    val paso = MutableStateFlow(1)   // 1 = zona, 2 = spot, 3 = pago

    fun cargarZonas() {
        viewModelScope.launch {
            val mapa = repo.obtenerZonas().data
            zonasUi.value = mapa.map { (nombre, spots) ->
                ZonaUi(nombre, spots)
            }
            zonas.value = mapa
        }
    }

    fun siguientePaso() {
        paso.value = paso.value + 1
    }
    fun retrocederPaso() {
            paso.value -= 1
    }


}

