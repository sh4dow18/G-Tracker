package sh4dow18.gtracker.backend_spring

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.RuntimeException
import java.time.LocalDateTime

data class ApiSubError(
    val code: String? = "NO-CODE",
    val message: String? = "NO MESSAGE",
)
data class ApiError(
    val localDateTime: String = LocalDateTime.now().toString(),
    val status: HttpStatus,
    val message: String? = null,
    val debugMessage: String? = null,
    var apiSubErrors: MutableList<ApiSubError> = mutableListOf(),
) {
    fun addSubError(apiError: ApiSubError) {
        apiSubErrors.add(apiError)
    }
}

class ElementAlreadyExists(message: String?) : RuntimeException(message)

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any>? {
        return ResponseEntity(apiError, apiError.status)
    }
    @ExceptionHandler(NoSuchElementException::class)
    fun elementNotFound(
        ex: java.lang.Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val apiError = ApiError(
            message = "Error Occurred",
            debugMessage = ex.message,
            status = HttpStatus.NOT_FOUND,
        )
        apiError.addSubError(ApiSubError("ELEMENT_NOT_FOUND", "Element not found"))
        logger.debug("Backend - G-Tracker {}", ex)
        return buildResponseEntity(apiError)
    }
    @ExceptionHandler(ElementAlreadyExists::class)
    fun elementAlreadyExists(
        ex: java.lang.Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val apiError = ApiError(
            message = "Error Occurred",
            debugMessage = ex.message,
            status = HttpStatus.CONFLICT,
        )
        apiError.addSubError(ApiSubError("CONFLICT_ELEMENT", "Conflict Element"))
        logger.debug("Backend - G-Tracker {}", ex)
        return buildResponseEntity(apiError)
    }
    @ExceptionHandler(Exception::class)
    fun handleAll(
        ex: java.lang.Exception,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val apiError = ApiError(
            message = "Error Occurred",
            debugMessage = ex.message,
            status = HttpStatus.INTERNAL_SERVER_ERROR,
        )
        apiError.addSubError(ApiSubError("INTERNAL_ERROR", "There is a serious error in the system"))
        logger.debug("Backend - G-Tracker {}", ex)
        return buildResponseEntity(apiError)
    }
}