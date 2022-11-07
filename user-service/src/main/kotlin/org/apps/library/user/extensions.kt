package org.apps.library.user

import org.apps.library.commons.dto.UserDTO
import org.apps.library.user.user.User
import org.apps.library.user.model.UserRequestDTO
import org.apps.library.user.model.UserResponseDTO

fun UserRequestDTO.toEntity(): User = User(name= name, loans = loans)
fun User.toDTO(): UserResponseDTO = UserResponseDTO(id!!, name, loans)
fun User.toMessageDTO(): UserDTO = UserDTO(id!!, name, loans)
