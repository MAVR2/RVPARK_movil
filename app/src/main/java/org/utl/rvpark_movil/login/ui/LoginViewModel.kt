package org.utl.rvpark_movil.login.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.login.data.model.LoginRequest
import org.utl.rvpark_movil.login.data.repository.LoginRepository
import org.utl.rvpark_movil.utils.preferences.UserRepository
import retrofit2.HttpException
import java.io.IOException

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
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }


    fun login(userRepository: UserRepository) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false) }

            val email = _uiState.value.email
            val password = _uiState.value.password

            if (email.isEmpty() || password.isEmpty()) {
                _uiState.update { it.copy(isLoading = false, error = "Los campos son obligatorios") }
                return@launch
            }

            try {
                // Login demo
                if (email == "admin" && password == "admin") {
                    userRepository.saveUser(
                        id = "1",
                        email = "admin",
                        name = "demo",
                        id_rv_park = "1",
                        token = "demo",
                        phone = "telefono demo",
                        rol = "demo"
                    )
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                    Log.d("Login", "Inicio de sesión DEMO")
                    return@launch
                }

                // Request real al backend
                val request = LoginRequest(nombre_usuario = email, password = password)
                val response = repository.Login(request)

                if (response.success && response.data != null) {
                    val data = response.data

                    userRepository.saveUser(
                        id = data.id_usuario.toString(),
                        email = data.nombre_usuario,
                        name = data.nombre,
                        id_rv_park = data.id_rv_park.toString(),
                        token = data.token,
                        phone = data.telefono,
                        rol = data.rol
                    )

                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                    Log.d("Login", "Login exitoso: ${data.nombre}")

                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = response.message ?: "Error desconocido del servidor"
                        )
                    }
                }

            } catch (e: HttpException) {
                val mensaje = e.response()?.errorBody()?.string() ?: "Error en la solicitud"
                _uiState.update { it.copy(isLoading = false, error = mensaje) }
                Log.d("Login", "HttpException: $mensaje")

            } catch (e: IOException) {
                _uiState.update { it.copy(isLoading = false, error = "Error de conexión") }
                Log.d("Login", "IOException: ${e.message}")

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error inesperado") }
                Log.d("Login", "Exception: ${e.message}")
            }
        }
    }
}