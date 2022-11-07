package org.apps.library.book.book

import org.apps.library.book.exceptions.BookNotFoundException
import org.apps.library.book.model.BookRequestDTO
import org.apps.library.book.model.BookResponseDTO
import org.apps.library.book.toDTO
import org.apps.library.book.toEntity
import org.apps.library.book.toMessageDTO
import org.apps.library.commons.dto.BookDTO
import org.apps.library.commons.dto.LoanMessageDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BookService(val bookRepository: BookRepository) {

    val logger: Logger = LoggerFactory.getLogger("info")

    fun findAll(): List<BookResponseDTO> = bookRepository.findAll().map{it.toDTO()}

    fun get(id: Long): BookResponseDTO = getById(id).toDTO()

    fun create(book: BookRequestDTO): BookResponseDTO = bookRepository.save(book.toEntity()).toDTO()

    fun update(id: Long, bookDTO: BookRequestDTO): BookResponseDTO {
        val book = getById(id)
        return with(bookDTO){
            bookRepository.save(Book(book.id, title,  copies))
        }.toDTO()
    }

    fun delete(id: Long) = bookRepository.deleteById(id)

    private fun getById(id: Long): Book = bookRepository.findById(id).orElseThrow { BookNotFoundException("book id $id not found") }

    fun reserve(message: LoanMessageDTO): BookDTO? {
        logger.info("loan event: $message")
        val book: Book = getById(message.idBook)
        book.reserve()
        return bookRepository.save(book).toMessageDTO()
    }

}
