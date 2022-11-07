package org.apps.library.loan.sm.services

import org.apps.library.commons.dto.BookMessageDTO
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.commons.dto.UserMessageDTO
import org.apps.library.commons.unwrap
import org.apps.library.loan.loan.Loan
import org.apps.library.loan.loan.LoanRepository
import org.apps.library.loan.model.Constants.Companion.LOAN_ID_HEADER
import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.loan.sm.LoanStateChangeInterceptor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.statemachine.StateMachine
import org.springframework.statemachine.access.StateMachineAccess
import org.springframework.statemachine.config.StateMachineFactory
import org.springframework.statemachine.support.DefaultStateMachineContext
import org.springframework.stereotype.Service


@Service
class StateMachineService(private val factory: StateMachineFactory<LoanStatus, LoanEvent>,
                          private val loanStateChangeInterceptor: LoanStateChangeInterceptor,
                          private val loanRepository: LoanRepository
) {

    val logger: Logger = LoggerFactory.getLogger("info")

    fun newLoan(loan: Loan) = sendEvent(loan, LoanEvent.USER_LOAN)

    fun processUserLoan(response: UserMessageDTO) {
        logger.info("User Loan Response: $response")
        val loan: Loan? = loanRepository.findById(response.loanId).unwrap()
        loan?.let {
            response.rejectionReason?.let {
                loan.status = LoanStatus.REJECTED
                loanRepository.save(loan)
                sendEvent(loan, LoanEvent.REJECT)
            }.run {
                sendEvent(loan, LoanEvent.USER_LOAN_SUCCESS)
                sendEventReserve(loan)
            }
        }
    }

    private fun sendEventReserve(loan: Loan) = sendEvent(loan, LoanEvent.RESERVE_BOOK)
    private fun sendEventApprove(loan: Loan) = sendEvent(loan, LoanEvent.APPROVE)


    fun processBookReserve(response: BookMessageDTO) {
        logger.info("Book reserve response: $response")
        val loan: Loan? = loanRepository.findById(response.loanId).unwrap()
        loan?.let {
            response.rejectionReason?.let {
                loan.status = LoanStatus.REJECTED
                loanRepository.save(loan)
                loanRepository.flush()
                sendEvent(loan, LoanEvent.INSUFFICIENT_BOOK_COPIES)
            }.run {
                sendEvent(loan, LoanEvent.RESERVE_BOOK_SUCCESS)
                sendEventApprove(loan)
            }
        }
    }

    fun processUserDeloan(response: UserMessageDTO) {
        logger.info("User DeLoan Response: $response")
        val loan: Loan? = loanRepository.findById(response.loanId).unwrap()
        loan?.let {
            loan.status = LoanStatus.USER_DELOAN_DONE
            loanRepository.save(loan)
            sendEvent(loan, LoanEvent.REJECT)
        }
    }

    private fun sendEvent(loan: Loan, event: LoanEvent) {

        logger.info("event $event: $loan")

        val sm: StateMachine<LoanStatus, LoanEvent> = build(loan)

        val msg: Message<LoanEvent> = MessageBuilder.withPayload(event)
            .setHeader(LOAN_ID_HEADER, loan.id.toString()).build()

        sm.sendEvent(msg)
    }

    private fun build(loan: Loan): StateMachine<LoanStatus, LoanEvent> {
        val sm: StateMachine<LoanStatus, LoanEvent> = factory.getStateMachine(loan.id.toString())
        sm.stop()

        sm.stateMachineAccessor.doWithAllRegions { sma: StateMachineAccess<LoanStatus, LoanEvent> ->
            sma.addStateMachineInterceptor(loanStateChangeInterceptor)
            sma.resetStateMachine(
                DefaultStateMachineContext(loan.status, null, null, null)
            )
        }
        sm.start()
        return sm
    }

    private fun sleep(millis: Long = 1000) = Thread.sleep(millis)

}
