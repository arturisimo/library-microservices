package org.apps.library.proxies.dto

import kotlinx.serialization.Serializable
import org.apps.library.commons.dto.BookDTO
import org.apps.library.commons.dto.UserDTO

data class LoanDetailDTO(val id: Long, val status: String, var book: BookDTO? = null, var user: UserDTO? = null)

@Serializable
data class LoanResponseDTO(val id: Long, val idUser: Long, val idBook: Long, val status: String)
