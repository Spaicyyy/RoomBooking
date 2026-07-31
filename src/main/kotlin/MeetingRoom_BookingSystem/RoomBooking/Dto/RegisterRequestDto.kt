package MeetingRoom_BookingSystem.RoomBooking.Dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequestDto(

    @field:NotBlank(message = "Name is required")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100")
    val name: String ,

    @field:NotBlank(message = "Email is required")
    @field:Size(max = 100, message = "Email must be within 100")
    @Email(message = "Email is valid")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @Size(min=3 , max = 100, message = "Password must be within 3 and 100")
    val password: String,
)