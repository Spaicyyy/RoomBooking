package MeetingRoom_BookingSystem.RoomBooking.Config

import MeetingRoom_BookingSystem.RoomBooking.Exceptions.UnauthorizedException
import MeetingRoom_BookingSystem.RoomBooking.Service.TokenBlacklistService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils,
    private val tokenBlacklistService: TokenBlacklistService,
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver
    ): OncePerRequestFilter(){

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
       try {
            val authHeader = request.getHeader("Authorization")
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                val token = authHeader.substring(7)

                if (jwtUtils.validateToken(token)) {

                    val jti = jwtUtils.getJtiFromToken(token)
                    if (tokenBlacklistService.isBlacklisted(jti)) {
                        throw UnauthorizedException("This token has been revoked (logged out)")
                    }

                    val userId = jwtUtils.getUserIdFromToken(token)
                    val roles = jwtUtils.getRolesFromToken(token)

                    val authorities = roles.map { SimpleGrantedAuthority(it) }

                    val authToken = UsernamePasswordAuthenticationToken(userId, null, authorities)

                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
            filterChain.doFilter(request, response)
       } catch(ex: Exception) {
              resolver.resolveException(request, response, null, ex)
       }
    }
}