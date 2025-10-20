package org.utl.rvpark_movil.login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    //UI
    val isLoanding: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)


class LoginViewModel : ViewModel(){
    //estado interno
    private val _uiState = MutableStateFlow(LoginUiState())
    //estado externo solo lectura
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword : String){
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoanding = true, error = null, isSuccess = false)
            try {
                if (_uiState.value.email == "admin" && _uiState.value.password == "admin") {
                    _uiState.value = _uiState.value.copy(isLoanding = false, isSuccess = true)
                    Log.d("debug", "Inicio Session")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoanding = false,
                        error = "Credenciales incorrectas",
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