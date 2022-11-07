package org.apps.library.book

import org.apps.library.book.book.Book
import org.apps.library.book.model.BookRequestDTO
import org.apps.library.book.model.BookResponseDTO
import org.apps.library.commons.dto.BookDTO

fun BookRequestDTO.toEntity(): Book = Book(title= title, copies = copies)
fun Book.toDTO(): BookResponseDTO = BookResponseDTO(id!!, title, copies)
fun Book.toMessageDTO(): BookDTO = BookDTO(id!!, title, copies)
