package MeetingRoom_BookingSystem.RoomBooking.Dto

import java.time.LocalDateTime

data class UserResponseDto (
    val id : Long,
    val username: String,
    val email: String,
    val roles: Collection<String>,
    val createdAt: LocalDateTime,
)