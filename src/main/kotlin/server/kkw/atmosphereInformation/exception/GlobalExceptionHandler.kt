package server.kkw.atmosphereInformation.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(APIRequestException::class)
    fun handleApiRequestException(e: APIRequestException): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "status" to e.statusCode,
            "error" to "API_ERROR",
            "message" to e.message
        )

        return ResponseEntity.status(e.statusCode).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(e: Exception): ResponseEntity<Map<String, Any>> {
        val message = e.message ?: "Unknown Exception"

        val body = mapOf(
            "status" to 500,
            "error" to e.javaClass,
            "message" to message
        )
        return ResponseEntity.status(500).body(body)
    }
}