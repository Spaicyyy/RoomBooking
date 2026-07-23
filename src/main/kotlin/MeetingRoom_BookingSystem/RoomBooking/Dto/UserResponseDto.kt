package MeetingRoom_BookingSystem.RoomBooking.Dto

import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import java.time.LocalDateTime

data class UserResponseDto (
    val id : Long,
    val username: String,
    val email: String,
    val roles : Set<RolesEnum>,
    val createdAt: LocalDateTime,
)