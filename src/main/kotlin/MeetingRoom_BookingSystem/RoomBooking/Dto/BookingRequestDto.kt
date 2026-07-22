package MeetingRoom_BookingSystem.RoomBooking.Dto

import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


data class BookingRequestDto(
    @field:NotNull(message = "User id cannot be null")
    val userId: Long,

    @field:NotNull(message = "Room id cannot be null")
    val roomId: Long,

    @field:NotNull(message = "Time cannot be blank")
    val startTime: LocalDateTime,

    @field:NotNull(message = "Time cannot be blank")
    val endTime: LocalDateTime,
)