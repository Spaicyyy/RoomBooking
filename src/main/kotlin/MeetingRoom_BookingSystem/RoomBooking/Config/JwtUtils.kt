package MeetingRoom_BookingSystem.RoomBooking.Config

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import javax.crypto.SecretKey
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import java.util.UUID


@Component
class JwtUtils (
    @Value("\${app.app.jwt.secret}") private val secretKey: String
){
    // Settings
    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    private val accessTokenExpiration = 900000 // 15 minutes

     val refreshExpiration = 30L // 30 days

    // Create refresh and access token
    fun generateAccessToken(username:String,userId:Long,roles: Collection<String>): String {
        val now = Date()
        val expiryDate = Date(now.time + accessTokenExpiration)

        return Jwts.builder()
            .subject(username)
            .claim("userId",userId)
            .claim("roles",roles)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(): String {
        return UUID.randomUUID().toString()
    }

    // Get info from access token
    fun getUsernameFromToken(token:String): String? {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun getRolesFromToken(token:String): Collection<String> {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims["roles"] as Collection<String>
    }

    fun getUserIdFromToken(token:String): Long {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

        return claims["userId"].toString().toLong()
    }

    fun validateToken(token:String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

            return true
        }catch (e:Exception){
            return false
        }


    }
}