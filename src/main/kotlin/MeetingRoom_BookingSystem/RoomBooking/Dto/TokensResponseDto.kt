package MeetingRoom_BookingSystem.RoomBooking.Dto

data class TokensResponseDto(
    val username: String,
    val accessToken: String,
    val refreshToken: String,
) {
}