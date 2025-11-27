package org.utl.rvpark_movil.chat.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.utl.rvpark_movil.chat.GeminiService
import org.utl.rvpark_movil.chat.model.*

data class ChatMessage(
    val id: Long = System.nanoTime(),
    val text: String,
    val isUser: Boolean
)

class ChatViewModel : ViewModel() {

    val messages = mutableStateListOf<ChatMessage>()
    private val apiKey = "AIzaSyAnP1z2rnbSwOgZNeRrB6CrYqPvXdQAhjM"

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        messages.add(ChatMessage(text = text, isUser = true))

        viewModelScope.launch {
            val reply = getGeminiResponse(text)
            messages.add(ChatMessage(text = reply, isUser = false))
        }
    }

    private suspend fun getGeminiResponse(userText: String): String = withContext(Dispatchers.IO) {

        val prompt = """
Eres Parki, el asistente oficial de la app RV Park.
Responde siempre con mensajes muy cortos y directos.

Solo puedes ayudar con:
- renta de espacios,
- costos (1200 USD/mes; se puede rentar por día, semana o mes),
- disponibilidad (150 espacios aprox.),
- eventos,
- información general,
- proceso para reservar,
- contacto: 555 456 963 — rvpark@email.com.

Si preguntan cómo rentar:
1. Abrir “Nuevo contrato”.
2. Seleccionar zona.
3. Seleccionar spot.
4. Elegir fechas.
5. Elegir método de pago.
6. Descargar PDF.

Si el usuario pregunta algo fuera de estos temas responde exactamente:
"Lo siento, no puedo ayudarte con esa operación. Pregúntame sobre costos, eventos o cómo realizar una renta."

Usuario: $userText
""".trimIndent()

        val request = GeminiRequest(
            contents = listOf(
                GeminiMessage(
                    role = "user",
                    parts = listOf(GeminiPart(prompt))
                )
            )
        )

        return@withContext try {
            val response = GeminiService.api.generateContent(apiKey, request)

            val text = response.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text

            restrictResponseToApp(text)

        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error Gemini: ${e.message}")
            "Lo siento, no puedo ayudarte con esa operación"
        }
    }

    private fun restrictResponseToApp(response: String?): String {

        if (response.isNullOrBlank())
            return "Lo siento, no puedo ayudarte con esa operación"

        val keywords = listOf("renta", "espacio", "zona", "spot", "evento", "costo", "contacto", "RV")

        return if (keywords.any { response.contains(it, ignoreCase = true) }) {
            response
        } else {
            "Lo siento, no puedo ayudarte con esa operación. Pregúntame sobre costos, eventos o cómo realizar una renta."
        }
    }
}
