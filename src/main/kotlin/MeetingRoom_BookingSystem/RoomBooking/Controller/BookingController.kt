package MeetingRoom_BookingSystem.RoomBooking.Controller

import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.BookingResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Service.BookingService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/booking")
class BookingController (
    private val bookingService: BookingService,
){

    @PostMapping
    fun createBooking(
        @Valid @RequestBody request: BookingRequestDto,
        authentication: Authentication,
    ): ResponseEntity<BookingResponseDto> {
        val userId = authentication.principal as Long
        val response = bookingService.createBooking(request, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}