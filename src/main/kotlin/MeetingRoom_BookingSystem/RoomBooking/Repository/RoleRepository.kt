package MeetingRoom_BookingSystem.RoomBooking.Repository


import MeetingRoom_BookingSystem.RoomBooking.Entity.Roles
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Roles, Long> {
    fun findByName(name: String): Roles?
}