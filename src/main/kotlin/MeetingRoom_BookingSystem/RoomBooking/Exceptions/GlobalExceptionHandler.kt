package MeetingRoom_BookingSystem.RoomBooking.Exceptions

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import MeetingRoom_BookingSystem.RoomBooking.Dto.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(
        ResourceNotFoundException::class,
        EntityNotFoundException::class,
        NoSuchElementException::class
    )    fun handlerNotFound(
        ex: Exception,
        request: HttpServletRequest,
    ) : ResponseEntity<ErrorResponse> {
        log.warn("Resource not found: {}", ex.message)
        return buildResponse(HttpStatus.NOT_FOUND,ex.message, request)
    }

    @ExceptionHandler(
        UserAlreadyExistsException::class,
        BadRequestException::class,
        IllegalArgumentException::class,
        HttpMessageNotReadableException::class
    )    fun handleBadRequest(
        ex: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        log.warn("Bad request: {}", ex.message)
        return buildResponse(HttpStatus.BAD_REQUEST, ex.message, request)
    }

    @ExceptionHandler(
        BadCredentialsException::class,
        UnauthorizedException::class
    )    fun handleUnauthorized(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.warn("Authentication failed: {}", ex.message)
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials or token", request)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("Validation failed: {}", errors)
        return buildResponse(HttpStatus.BAD_REQUEST, errors, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandled(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("Unhandled internal server error occurred", ex)
        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected internal error occurred",
            request
        )
    }


    private fun buildResponse(
        status: HttpStatus,
        message: String?,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val traceId = MDC.get("traceId")
        val errorBody = ErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = message,
            path = request.requestURI,
            traceId = traceId
        )
        return ResponseEntity.status(status).body(errorBody)
    }
}