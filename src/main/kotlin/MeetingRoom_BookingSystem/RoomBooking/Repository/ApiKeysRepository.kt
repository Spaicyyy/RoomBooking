package MeetingRoom_BookingSystem.RoomBooking.Repository

import MeetingRoom_BookingSystem.RoomBooking.Entity.ApiKeys
import org.springframework.data.jpa.repository.JpaRepository

interface ApiKeysRepository : JpaRepository<ApiKeys, Long> {
    fun findByKeyHashAndIsActiveTrue(keyHash: String): ApiKeys?
}