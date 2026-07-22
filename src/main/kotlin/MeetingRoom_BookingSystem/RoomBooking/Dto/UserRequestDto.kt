package com.techservice.technic_service.Dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class UserRequestDto(
    @field:NotBlank(message = "User name cannot be blank")
    @field:Size(max = 100, message = "User name must be within 100 characters")
    val username: String,

    @field:NotBlank(message = "Email can not be blank")
    @field:Email(message = "Invalid email format")
    @field:Size(max = 100)
    val email: String,

)
