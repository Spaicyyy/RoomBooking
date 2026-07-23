package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.RefreshToken
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUser(user: Users): RefreshToken?

    fun findByToken(token: String): RefreshToken?
}