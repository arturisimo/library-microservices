package org.apps.library.book.model


data class BookRequestDTO(val title: String, val copies: Int)

data class BookResponseDTO(val id: Long, val title: String, val copies: Int)
