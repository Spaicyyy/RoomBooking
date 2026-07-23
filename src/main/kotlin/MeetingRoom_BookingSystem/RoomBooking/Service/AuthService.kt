package MeetingRoom_BookingSystem.RoomBooking.Service

import MeetingRoom_BookingSystem.RoomBooking.Config.JwtUtils
import MeetingRoom_BookingSystem.RoomBooking.Dto.LoginRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.RegisterRequestDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.TokensResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.UserResponseDto
import MeetingRoom_BookingSystem.RoomBooking.Dto.toDto
import MeetingRoom_BookingSystem.RoomBooking.Entity.Users
import MeetingRoom_BookingSystem.RoomBooking.Repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import MeetingRoom_BookingSystem.RoomBooking.Repository.RefreshTokenRepository
import MeetingRoom_BookingSystem.RoomBooking.Repository.RolesRepository
import MeetingRoom_BookingSystem.RoomBooking.RolesEnum
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val roleRepository: RolesRepository
    ) {
    @Transactional
    fun registration(registerRequest: RegisterRequestDto): UserResponseDto {

        if(userRepository.existsByEmail(registerRequest.email)) {
            throw IllegalArgumentException("User with email ${registerRequest.email} already exists!")
        }

        val defaultRole = roleRepository.findByName(RolesEnum.USER)
            ?: throw EntityNotFoundException("Default role USER not found in database!")

        val user = Users(
            name = registerRequest.username ,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password)!!,
            roles = mutableSetOf(defaultRole)
       )

        val newUser = userRepository.save(user)

        return newUser.toDto()
    }

    fun login(loginRequest: LoginRequestDto): TokensResponseDto {
        val user = userRepository.findByEmail(loginRequest.email)
            ?: throw IllegalArgumentException("User with email ${loginRequest.email} doesn't exist!")

        if(!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw IllegalArgumentException("Wrong email or password!")
        }

        val roleNames = user.roles.map { it.name.name }

        val accessToken = jwtUtils.generateAccessToken(user.name , user.id, roleNames)

        val refreshToken = refreshTokenService.createRefreshToken(user.id)

        return TokensResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            username = user.name,
        )
    }

    fun refresh(refreshToken: String): TokensResponseDto {
        val token = refreshTokenRepository.findByToken(refreshToken)
        ?: throw IllegalArgumentException("Invalid or expired refresh token!")

        val validateToken = refreshTokenService.verifyExpiration(token)
        val user = validateToken.user
        val roleNames = user.roles.map { it.name.name }

        val newAccessToken = jwtUtils.generateAccessToken(user.name, user.id, roleNames)
        val newRefreshToken = refreshTokenService.createRefreshToken(user.id)

        return TokensResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            username = user.name,
        )
    }
}