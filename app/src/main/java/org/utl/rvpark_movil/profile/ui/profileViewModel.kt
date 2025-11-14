package org.utl.rvpark_movil.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.utl.rvpark_movil.login.data.repository.LoginRepository
import org.utl.rvpark_movil.utils.preferences.UserRepository

data class userUiState (
    val id: String = "",
    val email: String= "",
    val name: String= "",
    val lastName: String= "",
    val phone: String= "",
    val rol: String= "",

    //UI
    val isLoanding: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
    )

class ProfileViewModel: ViewModel(){


    fun updateEmail(newEmail: String){
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun updateName(newName: String){
        _uiState.value = _uiState.value.copy(name= newName)
    }

    fun updateLastName(newLastName: String){
        _uiState.value = _uiState.value.copy(lastName= newLastName)
    }

    fun updatePhone(newPhone: String){
        _uiState.value = _uiState.value.copy(phone= newPhone)
    }

    fun updateRol(newRol: String){
        _uiState.value = _uiState.value.copy(rol= newRol)
    }



    private val _uiState = MutableStateFlow(userUiState())
    val uiState: StateFlow<userUiState> = _uiState.asStateFlow()

    fun loadUser(userRepository: UserRepository) {
        viewModelScope.launch {
            userRepository.user2.collect { user ->
                _uiState.value = user.copy(isSuccess = true)
            }
        }
    }

}