package org.apps.library.book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class BookApp

fun main(args: Array<String>) {
    runApplication<BookApp>(*args)
}
