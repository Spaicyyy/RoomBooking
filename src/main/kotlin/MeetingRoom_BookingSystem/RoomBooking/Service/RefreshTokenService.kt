package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Config.JwtUtils
import MeetingRoom_BookingSystem.RoomBooking.Entity.RefreshToken
import MeetingRoom_BookingSystem.RoomBooking.Repository.RefreshTokenRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RefreshTokenService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtils: JwtUtils
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun createRefreshToken(userId: Long): String {
        log.info("Attempting to create or update refresh token for userId={}", userId)

        val user = userRepository.findById(userId)
            .orElseThrow {
                log.warn("Refresh token creation failed: User not found with id={}", userId)
                RuntimeException("User not found with id $userId")
            }

        val existingToken = refreshTokenRepository.findByUser(user)

        val newTokenString = jwtUtils.generateRefreshToken()
        val expiryDate = Instant.now().plus(jwtUtils.refreshExpiration, ChronoUnit.DAYS)

        if (existingToken != null) {
            log.debug("Existing refresh token found for userId={}. Updating token and expiration date", userId)
            existingToken.token = newTokenString
            existingToken.expiry_date = expiryDate
            refreshTokenRepository.save(existingToken)
        } else {
            log.debug("No existing refresh token found for userId={}. Creating a new one", userId)
            val refreshToken = RefreshToken(
                token = newTokenString,
                expiry_date = expiryDate,
                user = user
            )
            refreshTokenRepository.save(refreshToken)
        }

        log.info("Refresh token successfully created/updated for userId={}", userId)
        return newTokenString
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        log.debug("Verifying expiration for refresh token belonging to userId={}", token.user.id)

        if (token.isExpired()) {
            log.warn("Refresh token for userId={} has expired. Deleting token from database", token.user.id)
            refreshTokenRepository.delete(token)
            throw IllegalStateException("Refresh token was expired. Please log in again.")
        }

        return token
    }
}