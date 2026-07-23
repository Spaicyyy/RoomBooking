package MeetingRoom_BookingSystem.RoomBooking.Dto

import MeetingRoom_BookingSystem.RoomBooking.Entity.Bookings
import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import java.time.LocalDateTime


fun Users.toDto() = UserResponseDto(
    id = this.id,
    username = this.name,
    email = this.email,
    createdAt = this.createdAt ?: LocalDateTime.now(),
    roles = this.roles.map { it.name }.toSet(),
)

fun Rooms.toDto() = RoomResponseDto(
    id = this.id,
    name = this.name,
    capacity = this.capacity,
    description = this.description,
    createdAt = this.createdAt ?: LocalDateTime.now(),
)

fun Bookings.toDto() = BookingResponseDto(
    id = this.id,
    user = this.user,
    room = this.room,
    startTime = this.startTime ,
    endTime = this.endTime,
    createdAt = this.createdAt ?: LocalDateTime.now(),
)