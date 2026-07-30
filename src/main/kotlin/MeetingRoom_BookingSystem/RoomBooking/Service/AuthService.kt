package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterUserDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoleRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registration(requestRegister: RegisterUserDto) : UserResponseDto {
        if(userRepository.existsByEmail(requestRegister.email)) {
            throw IllegalArgumentException("User with email ${requestRegister.email} already exists.")
        }

        val defaultRole = roleRepository.findByName(RolesEnum.USER.name)

        val user = Users(
            name = requestRegister.username,
            email = requestRegister.email,
            password = passwordEncoder.encode(requestRegister.password)!!,
            roles = mutableSetOf(defaultRole)
        )

        val newUser = userRepository.save(user)
        return newUser.toDto()
    }
}