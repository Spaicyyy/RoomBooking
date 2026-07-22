package MeetingRoom_BookingSystem.RoomBooking.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "rooms")
class Rooms (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 50)
    var name: String = "",

    @Column(nullable = false,)
    var capacity: Int = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String = "",

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,
    ) {

}