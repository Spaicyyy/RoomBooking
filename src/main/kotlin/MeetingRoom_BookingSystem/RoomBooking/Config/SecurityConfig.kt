package MeetingRoom_BookingSystem.RoomBooking.Config

import MeetingRoom_BookingSystem.RoomBooking.Service.UserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailService,
    private val passwordEncoder: PasswordEncoder
) {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authenticationManager = DaoAuthenticationProvider(userDetailsService)
            .apply { setPasswordEncoder(passwordEncoder) }

        return ProviderManager(authenticationManager)
    }

    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/auth/register", "/api/auth/login", "/error").permitAll()
                    .anyRequest().authenticated()
            }
        return http.build()
    }
}

