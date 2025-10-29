package org.utl.rvpark_movil.profile.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun editarUser(){

    }

    fun editarPago(){

    }
}