package org.utl.rvpark_movil.login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.login.data.model.LoginRequest
import org.utl.rvpark_movil.login.data.repository.LoginRepository
import org.utl.rvpark_movil.utils.preferences.UserRepository

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login(userRepository: UserRepository) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, isSuccess = false)

            try {
                val email = _uiState.value.email
                val password = _uiState.value.password

                if (email == "admin" && password == "admin") {
                    userRepository.saveUser(
                        id_usuario ="1",
                        nombre_usuario = "admin",
                        nombre = "demo",
                        id_rv_park = "1",
                        token = "demo"
                    )

                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                    Log.d("debug", "Inicio de sesión DEMO")
                    return@launch
                }

                // Request real al backend
                val request = LoginRequest(
                    nombre_usuario = email,
                    password = password
                )

                val response = repository.Login(request)

                if (response.success) {

                    val data = response.data!!

                    Log.d("Debug", " ${data.nombre}")

                    userRepository.saveUser(
                        id_usuario = data.id_usuario.toString(),
                        nombre_usuario = data.nombre_usuario,
                        nombre = data.nombre,
                        id_rv_park = data.id_rv_park.toString(),
                        token = data.token
                    )

                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                    Log.d("debug", "Login exitoso")

                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = response.message ?: "Error desconocido"
                    )
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error de conexión"
                )
                Log.d("Debug", "$e")
            }
        }
    }
}