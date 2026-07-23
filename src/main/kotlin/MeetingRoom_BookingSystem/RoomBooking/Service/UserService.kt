package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import com.techservice.technic_service.Dto.UserRequestDto
import org.springframework.stereotype.Service

@Service
class UserService (
    val userRepository: UserRepository
) {

}