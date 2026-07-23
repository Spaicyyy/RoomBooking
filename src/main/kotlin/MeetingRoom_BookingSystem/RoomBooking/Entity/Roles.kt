package MeetingRoom_BookingSystem.RoomBooking.Entity

import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
class Roles (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ,

    @Enumerated(EnumType.STRING) // Указываем, что в БД будет храниться строка ("USER", "ADMIN")
    @Column(nullable = false, length = 100, unique = true)
    var name: RolesEnum
    )
{
}