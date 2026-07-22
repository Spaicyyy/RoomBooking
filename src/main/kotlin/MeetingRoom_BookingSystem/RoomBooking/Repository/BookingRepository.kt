package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.Bookings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface BookingRepository : JpaRepository<Bookings, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END 
        FROM Bookings b 
        WHERE b.room.id = :roomId 
        AND b.startTime < :endTime 
        AND b.endTime > :startTime
    """)
    fun isRoomOccupied(
        @Param("roomId") roomId: Long,
        @Param("startTime") startTime: LocalDateTime,
        @Param("endTime") endTime: LocalDateTime
    ): Boolean
}