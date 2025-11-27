import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun getGeminiResponse(userText: String): String = withContext(Dispatchers.IO) {
    val API_KEY = "AIzaSyAnP1z2rnbSwOgZNeRrB6CrYqPvXdQAhjM"
    val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$API_KEY")
    val conn = url.openConnection() as HttpURLConnection

    val systemPrompt = """
        Eres un asistente para la app RV Park, que sirve para renta de espacios para caravanas. 
        Solo debes responder preguntas relacionadas con la app, costos, eventos y cómo realizar una renta. 
        Para contacto puedes dar: número 555 456 963 y email rvpark@email.com.
        Si el usuario pregunta algo fuera de la app, responde: "Lo siento, no puedo ayudarte con esa operación. 
        Puedes preguntarme sobre los costos, eventos o cómo realizar una renta."
    """.trimIndent()

    conn.requestMethod = "POST"
    conn.setRequestProperty("Content-Type", "application/json")
    conn.doOutput = true

    val historial = mutableListOf<JSONObject>()
    historial.add(JSONObject().apply {
        put("role", "system")
        put("parts", listOf(JSONObject().apply { put("text", systemPrompt) }))
    })
    historial.add(JSONObject().apply {
        put("role", "user")
        put("parts", listOf(JSONObject().apply { put("text", userText) }))
    })

    val body = JSONObject().apply {
        put("contents", historial)
    }.toString()

    conn.outputStream.use { it.write(body.toByteArray()) }

    val responseText = conn.inputStream.bufferedReader().readText()
    val json = JSONObject(responseText)
    return@withContext json
        .getJSONArray("candidates")
        .getJSONObject(0)
        .getJSONObject("content")
        .getJSONArray("parts")
        .getJSONObject(0)
        .getString("text")
}
