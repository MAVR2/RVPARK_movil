package org.utl.rvpark_movil.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.home.data.model.Renta
import org.utl.rvpark_movil.home.repository.HomeRepository

data class homeUiState(
    val nombre_usuario: String = "",
    val rentas: List<Renta> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val repo: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _originalContratos = MutableStateFlow<List<Renta>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(false)
    private val _nombre = MutableStateFlow("")

    val uiState: StateFlow<homeUiState> = combine(
        _originalContratos,
        _searchQuery,
        _isLoading,
        _nombre
    ) { contratos, query, loading, nombre ->

        val filtered = if (query.isBlank()) {
            contratos
        } else {
            contratos.filter { it.id_renta.toString().contains(query, ignoreCase = true) }
        }

        homeUiState(
            nombre_usuario = nombre,
            rentas = filtered,
            searchQuery = query,
            isLoading = loading
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), homeUiState())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadContratos(id: String?) {
        if (id.isNullOrBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resp = repo.consultarRentas(id_usuario = id)
                _originalContratos.value = if (resp.success) resp.data else emptyList()
            } catch (e: Exception) {
                _originalContratos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
