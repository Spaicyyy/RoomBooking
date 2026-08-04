package MeetingRoom_BookingSystem.RoomBooking.Entity

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class Roles(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 100)
    var name: String = ""
)
