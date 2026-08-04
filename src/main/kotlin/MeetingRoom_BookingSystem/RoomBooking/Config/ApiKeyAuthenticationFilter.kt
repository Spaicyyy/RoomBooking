package MeetingRoom_BookingSystem.RoomBooking.Config

import MeetingRoom_BookingSystem.RoomBooking.Repository.ApiKeysRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotBlank
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.security.MessageDigest

@Component
class ApiKeyAuthenticationFilter(
    private val apiKeysRepository: ApiKeysRepository,
) : OncePerRequestFilter() {

    companion object {
        private const val API_KEY_HEADER = "X-API-KEY"
        private const val ROLE_API_CLIENT = "ROLE_API_CLIENT"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val rawApiKey = request.getHeader(API_KEY_HEADER)

        if(!rawApiKey.isNullOrBlank() && SecurityContextHolder.getContext().authentication == null) {
             val keyHash = hashSha256(rawApiKey)
             val apiKey = apiKeysRepository.findByKeyHashAndIsActiveTrue(keyHash)

            if(apiKey != null) {

                val authorities = listOf(SimpleGrantedAuthority(ROLE_API_CLIENT))
                val authentication = UsernamePasswordAuthenticationToken(
                    apiKey.ownerName, // Principal (имя владельца ключа)
                    null,
                    authorities
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun String?.isNotBlank(): Boolean = this != null && this.trim().isNotEmpty()

    private fun hashSha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}