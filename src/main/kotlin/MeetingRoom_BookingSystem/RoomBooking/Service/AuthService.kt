package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Config.JwtUtils
import MeetingRoom_BookingSystem.RoomBooking.Dto.LoginRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.TokensResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.RefreshTokenRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.RolesRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val roleRepository: RolesRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun registration(registerRequest: RegisterRequestDto): UserResponseDto {
        log.info("Attempting to register user with email={}", registerRequest.email)

        if (userRepository.existsByEmail(registerRequest.email)) {
            log.warn("Registration failed: User with email={} already exists", registerRequest.email)
            throw IllegalArgumentException("User with email ${registerRequest.email} already exists!")
        }

        val defaultRole = roleRepository.findByName(RolesEnum.USER)
            ?: run {
                log.error("Registration failed: Default role USER not found in database")
                throw EntityNotFoundException("Default role USER not found in database!")
            }

        val user = Users(
            name = registerRequest.username,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password)!!,
            roles = mutableSetOf(defaultRole)
        )

        val newUser = userRepository.save(user)
        log.info("User registered successfully with id={}, email={}", newUser.id, newUser.email)

        return newUser.toDto()
    }

    fun login(loginRequest: LoginRequestDto): TokensResponseDto {
        log.info("Attempting login for email={}", loginRequest.email)

        val user = userRepository.findByEmail(loginRequest.email)
            ?: run {
                log.warn("Login failed: User with email={} doesn't exist", loginRequest.email)
                throw IllegalArgumentException("User with email ${loginRequest.email} doesn't exist!")
            }

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            log.warn("Login failed: Invalid password attempt for email={}", loginRequest.email)
            throw IllegalArgumentException("Wrong email or password!")
        }

        val roleNames = user.roles.map { it.name.name }

        val accessToken = jwtUtils.generateAccessToken(user.name, user.id, roleNames)
        val refreshToken = refreshTokenService.createRefreshToken(user.id)

        log.info("User logged in successfully with id={}, email={}", user.id, user.email)

        return TokensResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            username = user.name,
        )
    }

    fun refresh(refreshToken: String): TokensResponseDto {
        log.info("Attempting to refresh access token")

        val token = refreshTokenRepository.findByToken(refreshToken)
            ?: run {
                log.warn("Token refresh failed: Provided refresh token is invalid or non-existent")
                throw IllegalArgumentException("Invalid or expired refresh token!")
            }

        val validateToken = refreshTokenService.verifyExpiration(token)
        val user = validateToken.user
        val roleNames = user.roles.map { it.name.name }

        val newAccessToken = jwtUtils.generateAccessToken(user.name, user.id, roleNames)
        val newRefreshToken = refreshTokenService.createRefreshToken(user.id)

        log.info("Tokens refreshed successfully for userId={}", user.id)

        return TokensResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            username = user.name,
        )
    }
}