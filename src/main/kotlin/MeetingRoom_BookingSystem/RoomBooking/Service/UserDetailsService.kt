package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService (
    val userRepository: UserRepository,
    ) : UserDetailsService{

    override fun loadUserByUsername(email:String): UserDetails {
            val user = userRepository.findByEmail(email)
                ?: throw EntityNotFoundException("User with email $email not found!")

            val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }

        return User(
            user.email,
            user.password,
            authorities
        )
    }
}