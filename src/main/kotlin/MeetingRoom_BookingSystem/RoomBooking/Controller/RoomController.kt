package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RoomResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Service.RoomService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms")
class RoomController (
    private val roomService: RoomService
){
    @PostMapping
    fun createRoom(
        @Valid @RequestBody roomRequestDto: RoomRequestDto,
    ) : ResponseEntity<RoomResponseDto> {
        val response = roomService.createRoom(roomRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{roomId}")
    fun getRoom(
        @PathVariable roomId:Long,
    ): ResponseEntity<RoomResponseDto> {
        val response = roomService.getRoom(roomId)
        return ResponseEntity.ok(response)
    }
}