package org.utl.rvpark_movil.chat.ui


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)


class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        messages.add(ChatMessage(text, true))
        simulateBotResponse(text)
    }

    private fun simulateBotResponse(userText: String) {
        viewModelScope.launch {
            messages.add(ChatMessage("Respuesta a: $userText", false))
        }
    }
}