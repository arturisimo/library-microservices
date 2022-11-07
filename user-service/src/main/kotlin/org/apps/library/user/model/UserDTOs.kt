package org.apps.library.user.model


data class UserRequestDTO(val name: String, val loans: Int)

data class UserResponseDTO(val id: Long, val name: String, val loans: Int)
