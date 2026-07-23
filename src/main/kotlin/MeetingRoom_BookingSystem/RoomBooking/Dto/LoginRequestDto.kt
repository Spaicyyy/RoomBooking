package MeetingRoom_BookingSystem.RoomBooking.Dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequestDto(
    @field:NotBlank(message = "Email can not be blank")
    @field:Email(message = "Invalid email format")
    @field:Size(max = 100)
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 3,max = 100 , message = "Password must be within 3-100 characters")
    val password: String,
) {

}