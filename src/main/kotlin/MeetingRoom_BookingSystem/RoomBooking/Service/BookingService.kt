package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Bookings
import MeetingRoom_BookingSystem.RoomBooking.Repository.BookingRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoomRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookingService(
    val bookingRepository: BookingRepository,
    val userRepository: UserRepository,
    val roomRepository: RoomRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun createBooking(bookingRequest: BookingRequestDto, userId: Long): BookingResponseDto {
        log.info("Attempting to create a booking. userId={}, roomId={}, startTime={}, endTime={}", userId, bookingRequest.roomId, bookingRequest.startTime, bookingRequest.endTime)

        if (bookingRequest.endTime.isBefore(bookingRequest.startTime) || bookingRequest.endTime.isEqual(bookingRequest.startTime)) {
            log.warn("Booking creation failed: Invalid time interval. startTime={}, endTime={}", bookingRequest.startTime, bookingRequest.endTime)
            throw IllegalArgumentException("End time must be strictly after start time")
        }

        val user = userRepository.findById(userId)
            .orElseThrow {
                log.warn("Booking creation failed: User not found with id={}", userId)
                EntityNotFoundException("User with id $userId not found")
            }

        val room = roomRepository.findById(bookingRequest.roomId)
            .orElseThrow {
                log.warn("Booking creation failed: Room not found with id={}", bookingRequest.roomId)
                EntityNotFoundException("Room with id ${bookingRequest.roomId} not found")
            }

        val isOccupied = bookingRepository.isRoomOccupied(
            roomId = bookingRequest.roomId,
            startTime = bookingRequest.startTime,
            endTime = bookingRequest.endTime
        )

        if (isOccupied) {
            log.warn("Booking creation failed: Room id={} is already occupied between {} and {}", bookingRequest.roomId, bookingRequest.startTime, bookingRequest.endTime)
            throw IllegalStateException("Room is already booked for this time period")
        }

        val booking = Bookings(
            user = user,
            room = room,
            startTime = bookingRequest.startTime,
            endTime = bookingRequest.endTime,
        )

        val savedBooking = bookingRepository.save(booking)
        log.info("Booking created successfully with id={} for userId={} in roomId={}", savedBooking.id, userId, room.id)

        return savedBooking.toDto()
    }
}