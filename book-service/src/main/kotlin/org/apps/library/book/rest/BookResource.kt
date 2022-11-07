package org.apps.library.book.rest

import java.net.URI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.apps.library.book.book.BookService
import org.apps.library.book.model.BookRequestDTO
import org.apps.library.book.model.BookResponseDTO
import org.springframework.http.HttpStatus
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
@RequestMapping(value = ["/api/v1/books"], produces = [MediaType.APPLICATION_JSON_VALUE])
class BookResource(val bookService: BookService) {

    @Operation(
        summary = """List of books""",
        responses = [
            ApiResponse(
                responseCode = "200", description = "List of books",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = BookResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping
    fun books(): ResponseEntity<List<BookResponseDTO>> = ResponseEntity.ok(bookService.findAll())

    @Operation(
        summary = "book by id",
        responses = [
            ApiResponse(
                responseCode = "200", description = "book by id",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = BookResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping(("/{id}"))
    fun book(
        @Parameter(description = "book id", example = "1")
        @PathVariable id: Long
    ): ResponseEntity<BookResponseDTO> = ResponseEntity.ok(bookService.get(id))

    @Operation(
        summary = "create a new book",
        responses = [
            ApiResponse(
                responseCode = "201", description = "new book",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = BookResponseDTO::class))
                ]
            )
        ]
    )
    @PostMapping
    fun createBook(@RequestBody @Valid bookDTO: BookRequestDTO): ResponseEntity<BookResponseDTO> {
        val book: BookResponseDTO = bookService.create(bookDTO)
        val location: URI = fromCurrentRequest().path("/{id}").buildAndExpand(book.id).toUri()
        return ResponseEntity.created(location).body(book)
    }

    @PutMapping("/{id}")
    fun updateBook(
        @Parameter(description = "book id", example = "1")
        @PathVariable id: Long,
        @RequestBody @Valid bookDTO: BookRequestDTO
    ): ResponseEntity<BookResponseDTO> {
        return ResponseEntity.ok().body(bookService.update(id, bookDTO))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteBook(@Parameter(description = "book id", example = "1") @PathVariable id: Long): ResponseEntity<Void> {
        bookService.delete(id)
        return ResponseEntity.noContent().build()
    }


}
