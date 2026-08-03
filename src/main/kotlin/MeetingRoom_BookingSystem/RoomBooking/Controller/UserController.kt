package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController (
    private val userService : UserService,
){
}