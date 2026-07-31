package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String): Users
}