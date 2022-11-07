package org.apps.library.commons.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoanMessageDTO(val id: Long, val idUser: Long, val idBook: Long, val status: String)
