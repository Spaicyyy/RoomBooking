package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoomRepository
import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class RoomService(
    val roomRepository: RoomRepository,
) {
    fun createRoom(roomRequestDto: RoomRequestDto): RoomResponseDto {
        val room = Rooms(
            name = roomRequestDto.name,
            capacity = roomRequestDto.capacity,
            description = roomRequestDto.description,
        )

        val newRoom = roomRepository.save(room)
        return newRoom.toDto()
    }

    fun getRoom(roomId: Long): RoomResponseDto {
        val room = roomRepository.findById(roomId)
            .orElseThrow { EntityNotFoundException("Room with id $roomId not found") }

        return room.toDto()
    }
}