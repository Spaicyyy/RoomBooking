package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Bookings
import MeetingRoom_BookingSystem.RoomBooking.Repository.BookingRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoomRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val userRepository: UserRepository,
    val roomRepository: RoomRepository,
    ) {
    fun createBooking(bookingRequest: BookingRequestDto): BookingResponseDto {
        if (bookingRequest.endTime.isBefore(bookingRequest.startTime) || bookingRequest.endTime.isEqual(bookingRequest.startTime)) {
            throw IllegalArgumentException("End time must be strictly after start time")
        }

        val user = userRepository.findById(bookingRequest.userId)
            .orElseThrow { EntityNotFoundException("User with id ${bookingRequest.userId} not found") }

        val room = roomRepository.findById(bookingRequest.roomId)
            .orElseThrow { EntityNotFoundException("Room with id ${bookingRequest.roomId} not found") }

        val isOccupied = bookingRepository.isRoomOccupied(
            roomId = bookingRequest.roomId,
            startTime = bookingRequest.startTime,
            endTime = bookingRequest.endTime
        )

        if (isOccupied) {
            throw IllegalStateException("Room is already booked for this time period")
        }

        val booking = Bookings(
            user = user,
            room = room,
            startTime = bookingRequest.startTime,
            endTime = bookingRequest.endTime,
        )

        val savedBooking = bookingRepository.save(booking)

        return savedBooking.toDto()
    }
}