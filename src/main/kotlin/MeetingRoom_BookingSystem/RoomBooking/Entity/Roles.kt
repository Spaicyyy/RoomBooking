package MeetingRoom_BookingSystem.RoomBooking.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class Roles (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0 ,

    @Column(nullable = false, unique = true, length = 50)
    var name: String = ""
) {}