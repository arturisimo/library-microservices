package org.apps.library.user.user

import org.apps.library.commons.dto.LoanMessageDTO
import org.apps.library.commons.dto.UserDTO
import org.apps.library.commons.dto.UserMessageDTO
import org.apps.library.commons.unwrap
import org.apps.library.user.exceptions.UserNotFoundException
import org.apps.library.user.model.UserRequestDTO
import org.apps.library.user.model.UserResponseDTO
import org.apps.library.user.toDTO
import org.apps.library.user.toEntity
import org.apps.library.user.toMessageDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(val userRepository: UserRepository) {

    val logger: Logger = LoggerFactory.getLogger("info")

    fun findAll(): List<UserResponseDTO> = userRepository.findAll().map{it.toDTO()}

    fun get(id: Long): UserResponseDTO = getById(id).toDTO()

    fun create(user: UserRequestDTO): UserResponseDTO = userRepository.save(user.toEntity()).toDTO()

    fun update(id: Long, userDTO: UserRequestDTO): UserResponseDTO {
        val user = getById(id)
        return with(userDTO){
            userRepository.save(User(user.id, name, loans))
        }.toDTO()
    }

    fun delete(id: Long) = userRepository.deleteById(id)

    fun loan(message: LoanMessageDTO): UserDTO {
        logger.info("loan event: $message")
        val user: User = getById(message.idUser)
        user.loan()
        return userRepository.save(user).toMessageDTO()
    }

    fun deloan(message: LoanMessageDTO): UserDTO {
        logger.info("deloan event: $message")
        val user: User = getById(message.idUser)
        user.deloan()
        return userRepository.save(user).toMessageDTO()
    }

    private fun getById(id: Long): User = userRepository.findById(id).orElseThrow { UserNotFoundException("user $id not found") }

}
