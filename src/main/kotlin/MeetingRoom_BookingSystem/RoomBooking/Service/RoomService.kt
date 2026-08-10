package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoomRepository
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class RoomService(
    val roomRepository: RoomRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun createRoom(roomRequestDto: RoomRequestDto): RoomResponseDto {
        log.info("Attempting to create a room. name={}, capacity={}", roomRequestDto.name, roomRequestDto.capacity)

        val room = Rooms(
            name = roomRequestDto.name,
            capacity = roomRequestDto.capacity,
            description = roomRequestDto.description,
        )

        val newRoom = roomRepository.save(room)
        log.info("Room created successfully with id={}", newRoom.id)

        return newRoom.toDto()
    }

    @Cacheable(value = ["rooms"], key = "#roomId")
    fun getRoom(roomId: Long): RoomResponseDto {
        log.debug("Fetching room with id={}", roomId)

        val room = roomRepository.findById(roomId)
            .orElseThrow {
                log.warn("Room retrieval failed: Room not found with id={}", roomId)
                EntityNotFoundException("Room with id $roomId not found")
            }

        return room.toDto()
    }
}