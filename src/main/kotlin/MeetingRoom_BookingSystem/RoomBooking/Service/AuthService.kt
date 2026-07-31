package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Roles
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.RolesRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val userRepository: UserRepository,
    val rolesRepository: RolesRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun register(requestRegister : RegisterRequestDto) : UserResponseDto {
        val defaultRoles = rolesRepository.findByName(RolesEnum.USER.name)

        val user = Users (
            name = requestRegister.name,
            email = requestRegister.email,
            password = passwordEncoder.encode(requestRegister.password)!!,
            roles = mutableSetOf(defaultRoles)
        )

        val newUser = userRepository.save(user)
        return newUser.toDto()
    }
}
