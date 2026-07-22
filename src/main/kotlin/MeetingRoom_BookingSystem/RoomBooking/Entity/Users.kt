package MeetingRoom_BookingSystem.RoomBooking.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class Users (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 100)
    var name: String = "",

    @Column(nullable = false, unique = true, length = 100)
    var email: String = "",

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    ) {

}