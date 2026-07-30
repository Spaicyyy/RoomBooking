package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Dto.LoginUserDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterUserDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController (
    private val authService: AuthService,
){
    @PostMapping("/register")
    fun registration(
        @Valid @RequestBody user: RegisterUserDto
    ): ResponseEntity<UserResponseDto> {
        val response = authService.registration(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody login: LoginUserDto,
        request: HttpServletRequest
    ): ResponseEntity<String> {
        try {
            request.session.invalidate()

            request.login(login.email, login.password)
            return ResponseEntity.ok("Login successfully! JSESSIONID cookie is set")
        }catch (e:Exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!")
        }
    }
    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<String> {
        request.logout()
        return ResponseEntity.ok("Logout successful!")
    }
}