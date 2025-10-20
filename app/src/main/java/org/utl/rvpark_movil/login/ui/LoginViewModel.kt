package org.utl.rvpark_movil.login.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.utils.preferences.UserRepository


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoanding: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword : String){
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login(userRepository: UserRepository) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoanding = true, error = null, isSuccess = false)
            try {
                if (_uiState.value.email == "admin" && _uiState.value.password == "admin") {

                    //usuario demo
                    userRepository.saveUser(
                        id_user = "1",
                        email = "admin",
                        name = "Admin",
                        lastName = "Test",
                        phone = "5551234567",
                        rol = "1"
                    )
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