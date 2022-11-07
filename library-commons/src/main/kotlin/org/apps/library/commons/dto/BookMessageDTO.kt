package org.apps.library.commons.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookMessageDTO(val loanId: Long, val book: BookDTO? = null, val rejectionReason: String? = null)

@Serializable
data class BookDTO(val id: Long, val title: String, val copies: Int)
