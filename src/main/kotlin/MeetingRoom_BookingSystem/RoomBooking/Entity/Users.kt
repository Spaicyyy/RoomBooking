package MeetingRoom_BookingSystem.RoomBooking.Entity

import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
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

    @Column(nullable = false, length = 100)
    var password: String= "",

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Roles> = mutableSetOf()
    ) {

}