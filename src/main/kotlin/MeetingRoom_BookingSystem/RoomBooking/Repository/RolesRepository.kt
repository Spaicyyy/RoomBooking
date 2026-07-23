package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.Roles
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import org.springframework.data.jpa.repository.JpaRepository

interface RolesRepository : JpaRepository<Roles, Long> {
    fun findByName(name: RolesEnum) : Roles?
}