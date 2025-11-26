package org.utl.rvpark_movil.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.profile.data.model.Persona
import org.utl.rvpark_movil.utils.preferences.UserRepository
import org.utl.rvpark_movil.profile.data.repository.PersonaRepository


data class userUiState(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phone: String = "",
    val rol: String = "",


    val loading: Boolean =false,
    val success: Boolean =false,
    val error: String? =""
)


class ProfileViewModel(
    private val personaRepository: PersonaRepository = PersonaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(userUiState())
    val uiState: StateFlow<userUiState> = _uiState

    fun loadUser(userRepository: UserRepository) {
        viewModelScope.launch {
            userRepository.user2.collect { user ->
                _uiState.value = _uiState.value.copy(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    phone = user.phone,
                    rol = user.rol,
                )
            }
        }
    }

    fun updateName(v: String) { _uiState.value = _uiState.value.copy(name = v,) }
    fun updateEmail(v: String) { _uiState.value = _uiState.value.copy(email = v,) }
    fun updatePhone(v: String) { _uiState.value = _uiState.value.copy(phone = v,) }

    fun savePersona(
        userRepository: UserRepository,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)

                val currentUser = userRepository.user2.first()

                val personaToUpdate = Persona(
                    id_Persona = currentUser.id,
                    nombre = uiState.value.name.takeIf { it.isNotBlank() },
                    telefono = uiState.value.phone.takeIf { it.isNotBlank() },
                    email = uiState.value.email.takeIf { it.isNotBlank() },
                    vehiculo = "Caravana",
                    direccion = null,
                    fecha_registro = null
                )

                val updatedPersona = personaRepository.updatePersona(personaToUpdate)

                userRepository.saveUser(
                    id = updatedPersona.data.id_Persona,
                    name = updatedPersona.data.nombre ?: "",
                    email = updatedPersona.data.email ?: "",
                    phone = updatedPersona.data.telefono ?: "",
                    rol = _uiState.value.rol
                )

                _uiState.value = _uiState.value.copy(
                    loading = false,
                    success = true,
                    name = updatedPersona.data.nombre ?: _uiState.value.name,
                    email = updatedPersona.data.email ?: _uiState.value.email,
                    phone = updatedPersona.data.telefono ?: _uiState.value.phone
                )

                onDone()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }


}
