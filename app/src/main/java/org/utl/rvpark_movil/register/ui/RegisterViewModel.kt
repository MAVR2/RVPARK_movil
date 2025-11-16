package org.utl.rvpark_movil.register.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.utl.rvpark_movil.register.data.model.ClienteRequest
import org.utl.rvpark_movil.register.data.repository.RegisterRepository
import retrofit2.HttpException



data class RegsiterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val password1: String = "",
    val password2: String = "",

    //UI
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel: ViewModel(){

    //llamada al back
    private val repository: RegisterRepository = RegisterRepository();

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

    fun updatePhone(newPhone: String){
        _uiState.value = _uiState.value.copy(phone = newPhone)
    }
    fun updateEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updatePassword1(newPassword1: String){
        _uiState.value = _uiState.value.copy(password1 = newPassword1)
    }

    fun updatePassword2(newPassword2: String){
        _uiState.value = _uiState.value.copy(password2 = newPassword2)
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }



    fun register() {
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false) }

            if (state.firstName.isEmpty() || state.lastName.isEmpty() || state.email.isEmpty()) {
                _uiState.update { it.copy(isLoading = false, error = "Todos los campos son obligatorios") }
                return@launch
            }

            if (state.password1 != state.password2) {
                _uiState.update { it.copy(isLoading = false, error = "Las contraseñas no coinciden") }
                return@launch
            }

            val request = ClienteRequest(
                nombre = "${state.firstName} ${state.lastName}",
                telefono = state.phone,
                nombre_usuario = state.email,
                password_hash = state.password1,
                rol = "Cliente"
            )

            try {
                val response = repository.registrarCliente(request)
                if (response.success) {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = response.message ?: "Error en el servidor") }
                }
            } catch (e: HttpException) {
                val mensaje = try {
                    JSONObject(e.response()?.errorBody()?.string() ?: "").optString("message", "Error en la solicitud")
                } catch (_: Exception) {
                    "Error en la solicitud"
                }
                _uiState.update { it.copy(isLoading = false, error = mensaje) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error de conexión") }
            }
        }
    }

}