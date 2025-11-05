package org.utl.rvpark_movil.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.home.data.Contrato
import org.utl.rvpark_movil.home.data.getContratos
import org.utl.rvpark_movil.utils.preferences.UserRepository

// Estado de la UI
data class homeUiState(
    val contratos: List<Contrato> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel(){

    private val _originalContratos = MutableStateFlow<List<Contrato>>(emptyList())

    private val _searchQuery = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<homeUiState> = combine(
        _originalContratos,
        _searchQuery,
        _isLoading
    ) { contratos, query, loading ->

        val filteredList = if(query.isBlank()){
            contratos
        }else{
            contratos.filter { contrato ->
                contrato.id_renta.toString().contains(query, ignoreCase = true)
            }
        }

        homeUiState(
            contratos = filteredList,
            searchQuery = query,
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = homeUiState()
    )

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
    }

    fun loadContratos(userRepository: UserRepository) {
        viewModelScope.launch {

            _isLoading.value = true

            try {
                val contratos = getContratos(userRepository)

                if (contratos != null) {
                    _originalContratos.value = contratos
                }

            } catch (e: Exception) {
            } finally {
                _isLoading.value = false
            }
        }
    }

}