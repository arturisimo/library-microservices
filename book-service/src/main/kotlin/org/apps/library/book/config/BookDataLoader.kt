package org.apps.library.book.config

import org.apps.library.book.book.BookRepository
import org.springframework.stereotype.Component
import org.springframework.boot.CommandLineRunner
import org.apps.library.book.book.Book

@Component
class BookDataLoader(
    private val bookRepository: BookRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val books = listOf<Book>(Book(title="Primavera para Madrid"), Book(title="¡Vivan las vacas!"), Book(title="Bajo un rayito de sol"))
        bookRepository.saveAll(books)
    }
}
