package MeetingRoom_BookingSystem.RoomBooking.Dto

import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import java.time.LocalDateTime

data class BookingResponseDto (
    val id: Long,
    val user: Users,
    val room: Rooms,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val createdAt: LocalDateTime,
)