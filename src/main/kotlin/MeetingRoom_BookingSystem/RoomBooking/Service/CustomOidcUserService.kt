package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.RoleRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = super.loadUser(userRequest)

        val attributes = oidcUser.attributes
        val email = attributes["email"] as? String ?: throw IllegalStateException("Email not provided by Google")
        val name = attributes["name"] as? String ?: "Unknown"

        val userOptional = userRepository.findByEmail(email)

        val defaultRoles = roleRepository.findByName(RolesEnum.USER.name)
            ?: throw EntityNotFoundException("Default role USER not found in database!")

        if (userOptional != null) {
            // Если пользователь уже есть, он доступен в переменной 'user' без всяких .get()
            // Здесь можно обновить имя или другие данные при необходимости
        } else {
            val newUser = Users(
                email = email,
                name = name,
                roles = mutableSetOf(defaultRoles)
            )
            userRepository.save(newUser)
        }
        return oidcUser
    }
}