package MeetingRoom_BookingSystem.RoomBooking.Config

import MeetingRoom_BookingSystem.RoomBooking.Service.CustomOidcUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val customOidcUserService: CustomOidcUserService,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/", "/login**").permitAll()
                auth.anyRequest().authenticated()
            }
            .oauth2Login { auth ->
                auth.userInfoEndpoint { user ->
                    user.oidcUserService ( customOidcUserService )

                    auth.defaultSuccessUrl("/home", true)
                }
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/")
            }

        return http.build()
    }
}