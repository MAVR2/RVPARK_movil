package org.utl.rvpark_movil.chat.model

data class GeminiPart(
    val text: String
)
data class GeminiRequest(
    val contents: List<GeminiMessage>
)

data class GeminiContent(
    val role: String?,
    val parts: List<GeminiPart>
)

data class GeminiCandidate(
    val content: GeminiContent,
    val finishReason: String?
)
data class GeminiResponse(
    val candidates: List<GeminiCandidate>
)

data class GeminiMessage(
    val role: String = "user",
    val parts: List<GeminiPart>
)

