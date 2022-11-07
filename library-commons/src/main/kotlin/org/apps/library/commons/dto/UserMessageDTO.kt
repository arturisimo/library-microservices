package org.apps.library.commons.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserMessageDTO(val loanId: Long, val user: UserDTO? = null, val rejectionReason: String? = null)

@Serializable
data class UserDTO(val id: Long, val name: String, val loans: Int)
