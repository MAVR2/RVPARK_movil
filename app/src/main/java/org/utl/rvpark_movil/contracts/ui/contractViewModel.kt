package org.utl.rvpark_movil.contracts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.contracts.repository.DetailRepository
import org.utl.rvpark_movil.parking.data.model.RentaCompleta


data class ContractUiState(
    val renta: RentaCompleta? = null,
    val loading: Boolean = false,
    val error: String? = null
)


class ContractViewModel(
    private val repo: DetailRepository = DetailRepository()
): ViewModel(){

    private val _uiState = MutableStateFlow(ContractUiState())
    val uiState: StateFlow<ContractUiState> = _uiState


    fun loadRenta(id: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = ContractUiState(loading = true)

                val response = repo.consultarRenta(id.toString())

                _uiState.value = ContractUiState(
                    renta = response.data,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _uiState.value = ContractUiState(
                    renta = null,
                    loading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
        }

}
