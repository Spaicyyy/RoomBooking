package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Dto.LoginRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.TokensResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.RestrictsSuspension

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(
    private val authService: AuthService,
){
    @PostMapping(value = ["/register"])
    fun registration(
        @Valid @RequestBody registerRequest: RegisterRequestDto,
    ): ResponseEntity<UserResponseDto> {
        val response = authService.registration(registerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping(value = ["/login"])
    fun login(
        @Valid @RequestBody loginRequest: LoginRequestDto,
    ): ResponseEntity<TokensResponseDto> {
        val response = authService.login(loginRequest)
        return ResponseEntity.ok(response)
    }

    @PostMapping(value = ["/refresh"])
    fun refresh(
        @RequestParam refreshToken: String,
    ):ResponseEntity<TokensResponseDto> {
        val response = authService.refresh(refreshToken)
        return ResponseEntity.ok(response)
    }
}