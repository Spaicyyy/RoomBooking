package MeetingRoom_BookingSystem.RoomBooking.Exceptions

sealed class BusinessException(message: String) : RuntimeException(message)

class ResourceNotFoundException(message: String) : BusinessException(message)
class UserAlreadyExistsException(message: String) : BusinessException(message)
class BadRequestException(message: String) : BusinessException(message)
class UnauthorizedException(message: String) : BusinessException(message)