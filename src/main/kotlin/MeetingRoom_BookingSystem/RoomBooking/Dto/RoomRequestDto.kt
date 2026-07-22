package MeetingRoom_BookingSystem.RoomBooking.Dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RoomRequestDto(
    @field:NotBlank(message = "Room name cannot be blank")
    @field:Size(max = 50, message = "User name must be within 50 characters")
    val name: String,

    @field:NotNull(message = "Room capacity cannot be null")
    val capacity : Int,

    @field:NotBlank(message = "Room description cannot be blank")
    @field:Size(max = 500, message = "Room description must be within 500 characters")
    val description : String,
)
