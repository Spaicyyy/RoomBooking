package MeetingRoom_BookingSystem.RoomBooking.Config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val id: Long,
    private val email: String,
    private val pass: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = pass
    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}