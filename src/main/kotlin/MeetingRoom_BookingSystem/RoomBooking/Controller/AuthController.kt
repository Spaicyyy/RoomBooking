package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Service.CustomOAuth2UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    val customOAuth2UserService: CustomOAuth2UserService
) {
}