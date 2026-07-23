package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Config.JwtUtils
import MeetingRoom_BookingSystem.RoomBooking.Entity.RefreshToken
import MeetingRoom_BookingSystem.RoomBooking.Repository.RefreshTokenRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class RefreshTokenService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository ,
    private val jwtUtils: JwtUtils
) {
    fun createRefreshToken(userId:Long): String {
        val user = userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found with id $userId") }

        val existingToken = refreshTokenRepository.findByUser(user)

        val newTokenString = jwtUtils.generateRefreshToken()
        val expiryDate = Instant.now().plus(jwtUtils.refreshExpiration, ChronoUnit.DAYS)

        if(existingToken != null){
            existingToken.token = newTokenString
            existingToken.expiry_date = expiryDate
            refreshTokenRepository.save(existingToken)
        }else {
            val refreshToken = RefreshToken(
                token = newTokenString,
                expiry_date = expiryDate,
                user = user
            )

            refreshTokenRepository.save(refreshToken)
        }
        return newTokenString
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if(token.isExpired()) {
            refreshTokenRepository.delete(token)
            throw IllegalStateException("Refresh token was expired. Please log in again.")
        }
        return token
    }
}