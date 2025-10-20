package org.utl.rvpark_movil.register.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegsiterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val password1: String = "",
    val password2: String = "",
    val rol: Int = 0,

    //UI
    val isLoanding: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel: ViewModel(){

    //estado interno
    private val _uiState = MutableStateFlow(RegsiterUiState())
    //estado externo solo lectura
    val uiState: StateFlow<RegsiterUiState> = _uiState.asStateFlow()

    fun updateFirstName(newName: String){
        _uiState.value = _uiState.value.copy(firstName = newName)
    }

    fun updateLastName(newLastName: String){
        _uiState.value = _uiState.value.copy(lastName = newLastName)
    }

    fun updatePassword1(newPassword1: String){
        _uiState.value = _uiState.value.copy(password1 = newPassword1)
    }

    fun updatePassword2(newPassword2: String){
        _uiState.value = _uiState.value.copy(password2 = newPassword2)
    }

    fun updateRol(newRol: Int){
        _uiState.value = _uiState.value.copy(rol = newRol)
    }

    fun register() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoanding = true, error = null, isSuccess = false)
            try {
                if (_uiState.value.password1 == _uiState.value.password2) {
                    if (
                        _uiState.value.firstName.isEmpty() ||
                        _uiState.value.lastName.isEmpty() ||
                        _uiState.value.rol == 0
                    ) {
                        _uiState.value = _uiState.value.copy(
                            isLoanding = false,
                            error = "Faltan datos obligatorios",
                            isSuccess = false
                        )
                        Log.d("Debug", "faltan datos")
                    } else {
                        _uiState.value = _uiState.value.copy(isLoanding = false, isSuccess = true)
                        Log.d("Debug", "Registrado")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoanding = false,
                        error = "Passwords no coinciden",
                        isSuccess = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoanding = false,
                    error = "Error de conexión",
                    isSuccess = false
                )
            }
        }
    }




}