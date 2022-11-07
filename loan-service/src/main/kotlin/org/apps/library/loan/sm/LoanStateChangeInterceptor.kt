package org.apps.library.loan.sm

import org.apps.library.loan.loan.Loan
import org.apps.library.loan.loan.LoanRepository
import org.apps.library.loan.model.Constants.Companion.LOAN_ID_HEADER
import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.commons.unwrap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.state.State
import org.springframework.statemachine.support.StateMachineInterceptorAdapter
import org.springframework.statemachine.transition.Transition
import org.springframework.stereotype.Component

@Component
class LoanStateChangeInterceptor(val loanRepository: LoanRepository): StateMachineInterceptorAdapter<LoanStatus, LoanEvent>() {

    val logger: Logger = LoggerFactory.getLogger("info")

    override fun preStateChange(
        state: State<LoanStatus, LoanEvent>?,
        message: Message<LoanEvent>?,
        transition: Transition<LoanStatus, LoanEvent>?,
        stateMachine: StateMachine<LoanStatus, LoanEvent>?,
        rootStateMachine: StateMachine<LoanStatus, LoanEvent>?
    ) {

        message?.let { msg ->
            val loanId: Long = (msg.headers[LOAN_ID_HEADER] as String).toLong()
            logger.debug("Saving state for loan id: $loanId Status: ${state?.id}")
            val loan: Loan? = loanRepository.findById(loanId).unwrap()
            loan?.let {
                it.status = state?.id!!
                loanRepository.saveAndFlush(it)
            }

        }

    }

}
