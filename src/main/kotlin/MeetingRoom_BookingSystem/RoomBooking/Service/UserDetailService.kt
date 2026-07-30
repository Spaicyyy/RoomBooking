package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Config.CustomUserDetails
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw EntityNotFoundException("User with email $email not found!")

        val authorities = user.roles.map { SimpleGrantedAuthority(it.name)}
        return CustomUserDetails(
                user.id,
                user.email,
                user.password,
                authorities
        )
    }
}