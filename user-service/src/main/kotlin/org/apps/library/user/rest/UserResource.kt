package org.apps.library.user.rest

import java.net.URI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.apps.library.user.user.UserService
import org.apps.library.user.model.UserRequestDTO
import org.apps.library.user.model.UserResponseDTO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import javax.validation.Valid


@RestController
@RequestMapping(value = ["/api/v1/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserResource(val userService: UserService) {

    @Operation(
        summary = """List of users""",
        responses = [
            ApiResponse(
                responseCode = "200", description = "List of users",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = UserResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping
    fun users(): ResponseEntity<List<UserResponseDTO>> = ResponseEntity.ok(userService.findAll())

    @Operation(
        summary = "user by id",
        responses = [
            ApiResponse(
                responseCode = "200", description = "user by id",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = UserResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping(("/{id}"))
    fun user(
        @Parameter(description = "user id", example = "1")
        @PathVariable id: Long
    ): ResponseEntity<UserResponseDTO> = ResponseEntity.ok(userService.get(id))

    @Operation(
        summary = "create a new user",
        responses = [
            ApiResponse(
                responseCode = "201", description = "new user",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = UserResponseDTO::class))
                ]
            )
        ]
    )
    @PostMapping
    fun createUser(@RequestBody @Valid userDTO: UserRequestDTO): ResponseEntity<UserResponseDTO> {
        val user: UserResponseDTO = userService.create(userDTO)
        val location: URI = fromCurrentRequest().path("/{id}").buildAndExpand(user.id).toUri()
        return ResponseEntity.created(location).body(user)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @Parameter(description = "user id", example = "1")
        @PathVariable id: Long,
        @RequestBody @Valid userDTO: UserRequestDTO
    ): ResponseEntity<UserResponseDTO> {
        return ResponseEntity.ok().body(userService.update(id, userDTO))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteUser(@Parameter(description = "user id", example = "1") @PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }


}
