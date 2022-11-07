package org.apps.library.loan.model

import kotlinx.serialization.Serializable
import org.apps.library.commons.dto.Constants.Companion.LoanStatus


@Serializable
data class LoanRequestDTO(val idUser: Long, val idBook: Long)

@Serializable
data class LoanResponseDTO(val id: Long, val idUser: Long, val idBook: Long, val status: LoanStatus)
