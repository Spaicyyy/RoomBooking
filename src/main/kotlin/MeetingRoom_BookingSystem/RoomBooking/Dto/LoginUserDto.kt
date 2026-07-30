package MeetingRoom_BookingSystem.RoomBooking.Dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginUserDto(

    @field:NotBlank(message = "Email is required")
    @field:Size(min = 1, max = 255, message = "Email must be between 1 and 255")
    @field:Email(message = "Invalid email")
    val email: String ,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 255, message = "Password must be between 6 and 255")
    val password: String,
)
