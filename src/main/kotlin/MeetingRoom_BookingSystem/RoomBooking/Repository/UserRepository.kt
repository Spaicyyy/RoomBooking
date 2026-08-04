package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String): Users?
}