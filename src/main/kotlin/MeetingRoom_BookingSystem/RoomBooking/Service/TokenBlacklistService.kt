package MeetingRoom_BookingSystem.RoomBooking.Service

import org.springframework.stereotype.Service
import org.springframework.data.redis.core.StringRedisTemplate
import java.util.concurrent.TimeUnit

@Service
class TokenBlacklistService (
        private val redisTemplate: StringRedisTemplate
) {

    companion object {
        private const val BLACKLIST_PREFIX = "jwt:blacklist:"
    }

    fun addToBlacklist(jti:String , timeToLive:Long) {
            if(timeToLive > 0 ) {
                val key = "$BLACKLIST_PREFIX$jti"
                redisTemplate.opsForValue().set(key,"revoked",timeToLive, TimeUnit.MILLISECONDS)
            }
    }

    fun isBlacklisted(jti:String) : Boolean {
        val key = "$BLACKLIST_PREFIX$jti"
        return redisTemplate.hasKey(key) == true
    }
}