package MeetingRoom_BookingSystem.RoomBooking.Config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils,
    ): OncePerRequestFilter(){

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            val token = authHeader.substring(7)

            if(jwtUtils.validateToken(token)){
                val userId = jwtUtils.getUserIdFromToken(token)
                val roles = jwtUtils.getRolesFromToken(token)

                val authorities = roles.map { SimpleGrantedAuthority(it) }

                val authToken = UsernamePasswordAuthenticationToken(userId, null, authorities)

                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}