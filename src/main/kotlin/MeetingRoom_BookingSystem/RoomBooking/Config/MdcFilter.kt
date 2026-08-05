package MeetingRoom_BookingSystem.RoomBooking.Config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
class MdcFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val traceId = request.getHeader("X-Trace-Id") ?: UUID.randomUUID().toString().take(8)

        MDC.put("traceId", traceId)
        MDC.put("httpReq", "${request.method} ${request.requestURI}")

        val auth = SecurityContextHolder.getContext().authentication

        if (auth != null && auth.isAuthenticated && auth.principal != "anonymousUser") {
            MDC.put("user", auth.name)
        } else {
            MDC.put("user", "ANONYMOUS")
        }

        response.setHeader("X-Trace-Id", traceId)


        try {
            filterChain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }
}