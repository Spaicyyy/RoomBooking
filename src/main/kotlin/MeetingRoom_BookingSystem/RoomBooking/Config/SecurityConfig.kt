package MeetingRoom_BookingSystem.RoomBooking.Config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtFilter : JwtFilter
) {

    @Bean
    fun apiSecurityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests{ auth ->
                auth
                    .requestMatchers(
                        "/api/auth/register","/api/auth/login","/api/auth/refresh","/error"
                    ).permitAll()

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}