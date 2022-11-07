package org.apps.library.loan.loan

import org.apps.library.loan.exceptions.LoanNotFoundException
import org.apps.library.loan.model.LoanRequestDTO
import org.apps.library.loan.model.LoanResponseDTO
import org.apps.library.loan.toDTO
import org.apps.library.loan.toEntity
import org.springframework.stereotype.Service

@Service
class LoanService(val loanRepository: LoanRepository) {

    fun findAll(): List<LoanResponseDTO> = loanRepository.findAll().map{it.toDTO()}

    fun get(id: Long): LoanResponseDTO = getById(id).toDTO()

    fun create(loan: LoanRequestDTO): LoanResponseDTO = loanRepository.save(loan.toEntity()).toDTO()

    fun save(loan: Loan): Loan = loanRepository.save(loan)

    fun update(id: Long, loanDTO: LoanRequestDTO): LoanResponseDTO {
        val loan = getById(id)
        return with(loanDTO){
            loanRepository.save(Loan(loan.id, idBook, idUser))
        }.toDTO()
    }

    fun delete(id: Long) = loanRepository.deleteById(id)

    fun getById(id: Long): Loan = loanRepository.findById(id).orElseThrow { LoanNotFoundException("loan $id not found") }

}
