package com.library.system.model

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    data class ApiError(
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val status: Int,
        val error: String,
        val message: String?,
        val path: String,
    )

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val errors =
            ex.bindingResult.fieldErrors.joinToString("; ") {
                "${it.field}: ${it.defaultMessage}"
            }

        val path = request.getDescription(false).removePrefix("uri=")

        val errorResponse =
            ApiError(
                status = status.value(),
                error = "Validation Failed",
                message = errors,
                path = path,
            )

        return ResponseEntity(errorResponse, status)
    }

    // Updated to handle ResourceNotFoundException globally
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(
        ex: ResourceNotFoundException,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.NOT_FOUND.value(),
                error = "Not Found",
                message = ex.message,
                path = path,
            )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(
        ex: NoSuchElementException,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.NOT_FOUND.value(),
                error = "Not Found",
                message = ex.message,
                path = path,
            )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BookNotAvailableException::class)
    fun handleBookNotAvailable(
        ex: BookNotAvailableException,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.CONFLICT.value(),
                error = "Conflict",
                message = ex.message,
                path = path,
            )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BookAlreadyReturnedException::class)
    fun handleBookAlreadyReturned(
        ex: BookAlreadyReturnedException,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.CONFLICT.value(),
                error = "Conflict",
                message = ex.message,
                path = path,
            )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(BorrowingLimitExceededException::class)
    fun handleBorrowingLimitExceeded(
        ex: BorrowingLimitExceededException,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.CONFLICT.value(), // Or 422 Unprocessable Entity
                error = "Conflict",
                message = ex.message,
                path = path,
            )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<ApiError> {
        val path = request.getDescription(false).removePrefix("uri=")
        val errorResponse =
            ApiError(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error",
                message = ex.message,
                path = path,
            )
        ex.printStackTrace() // Helpful for debugging, consider removing in production
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
