package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.Rooms
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Rooms, Long> {
}