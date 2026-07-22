package MeetingRoom_BookingSystem.RoomBooking.Dto

import java.time.LocalDateTime

data class RoomResponseDto (
    val id : Long,
    val name: String,
    val capacity: Int,
    val description: String,
    val createdAt: LocalDateTime,
)