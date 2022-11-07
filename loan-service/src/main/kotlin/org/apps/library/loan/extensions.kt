package org.apps.library.loan

import org.apps.library.commons.dto.LoanMessageDTO
import org.apps.library.loan.loan.Loan
import org.apps.library.loan.model.LoanRequestDTO
import org.apps.library.loan.model.LoanResponseDTO
import java.util.*

fun LoanRequestDTO.toEntity(): Loan = Loan(idBook= idBook, idUser = idUser)
fun Loan.toDTO(): LoanResponseDTO = LoanResponseDTO(id!!, idUser, idBook, status)
fun LoanResponseDTO.toMessageDTO(): LoanMessageDTO = LoanMessageDTO(id, idUser, idBook, status.name)
