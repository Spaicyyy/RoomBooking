package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.RolesRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService (
    val userRepository: UserRepository,
    val rolesRepository: RolesRepository
) : DefaultOAuth2UserService() {

    private fun registerNewOAuth2User(email: String, name: String):  Users {
            val defaultRoles = rolesRepository.findByName(RolesEnum.USER.name)
                ?: throw EntityNotFoundException("Default role USER not found in database! Make sure to seed roles table.")

            val newUser = Users(
                name = name,
                email = email,
                roles = mutableSetOf(defaultRoles)
            )
        return userRepository.save(newUser)
    }

    override fun loadUser(userRequest: OAuth2UserRequest) : OAuth2User {
        val oauth2User = super.loadUser(userRequest)

        val login = oauth2User.attributes["login"]?.toString() ?: "Unknown_user"
        val email = oauth2User.attributes["email"]?.toString() ?: "$login@github.com"
        val name = oauth2User.attributes["name"]?.toString() ?: login

        val user = userRepository.findByEmail(email) ?: registerNewOAuth2User(email, name)

        val authorities = user.roles.map { SimpleGrantedAuthority(it.name)}

        return DefaultOAuth2User(
            authorities,
            oauth2User.attributes,
            "login"
        )
    }
}