package org.apps.library.loan.sm.actions

import org.springframework.statemachine.StateContext
import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.commons.unwrap
import org.apps.library.loan.loan.LoanRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class SaveStatusAction<T, U>(val loanRepository: LoanRepository) : LibraryAction<LoanStatus, LoanEvent>() {

    val logger: Logger = LoggerFactory.getLogger("info")

    override fun execute(context: StateContext<LoanStatus, LoanEvent>) {
        val loanId = getLoanId(context)
        loanRepository.findById(loanId).unwrap()?.let {
            logger.info("$loanId: ${context.event}: ${it.status} > ${context.target.id}")
            it.status = context.target.id
            loanRepository.save(it)
            loanRepository.flush()
        } ?: run {
            logger.error("loan $loanId not found")
        }
    }

}
