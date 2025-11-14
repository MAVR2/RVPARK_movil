package org.utl.rvpark_movil.register.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.register.data.model.ClienteRequest
import org.utl.rvpark_movil.register.data.repository.RegisterRepository

data class RegsiterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val password1: String = "",
    val password2: String = "",

    //UI
    val isLoanding: Boolean = false,
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


    fun register() {
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.value = _uiState.value.copy(isLoanding = true, error = null, isSuccess = false)
            try {
                if(state.password1 != state.password2){
                    _uiState.value = state.copy(isLoanding = false, error = "Las contraseñas no coinciden")
                    return@launch
            }
                if(state.firstName.isEmpty() || state.lastName.isEmpty() || state.email.isEmpty()){
                    _uiState.value = state.copy(isLoanding = false, error="Todos los campos son obligatorios")
                    return@launch
                }

                //preparar datos
                val request = ClienteRequest(
                    nombre = state.firstName + " " + state.lastName,
                    telefono = state.phone,
                    email = state.email,
                    direccion = "",
                    nombre_usuario = state.email,
                    password_hash = state.password1,
                    rol = "Cliente"
                )

                val response = repository.registrarCliente(request)

                if(response.success){
                    _uiState.value = state.copy(isLoanding = false, isSuccess = true)
                    Log.d("debug", "registro exitoso")
                }else{
                    _uiState.value = state.copy(isLoanding = false, error = response.message ?: "Error en el servidor")
                }


            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoanding = false,
                    error = "Error de conexión",
                    isSuccess = false
                )
                Log.d("debug", "${e}")
            }
        }
    }
}