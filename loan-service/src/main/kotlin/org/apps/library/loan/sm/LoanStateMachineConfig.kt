package org.apps.library.loan.sm

import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.loan.sm.actions.ApproveLoanAction
import org.apps.library.loan.sm.actions.RejectLoanAction
import org.apps.library.loan.sm.actions.ReserveBookAction
import org.apps.library.loan.sm.actions.ReserveBookFailureAction
import org.apps.library.loan.sm.actions.UserLoanAction
import org.apps.library.loan.sm.actions.UserLoanFailureAction
import org.apps.library.loan.sm.actions.UserLoanSuccessAction
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import java.util.*

@Configuration
@EnableStateMachineFactory
class LoanStateMachineConfig(
    val userLoanAction: UserLoanAction,
    val userDeLoanAction: UserLoanAction,
    val userLoanSuccessAction: UserLoanSuccessAction,
    val userLoanFailureAction: UserLoanFailureAction,
    val reserveBookAction: ReserveBookAction,
    val reserveBookFailureAction: ReserveBookFailureAction,
    val approveLoanAction: ApproveLoanAction,
    val rejectAction: RejectLoanAction,
) : StateMachineConfigurerAdapter<LoanStatus, LoanEvent>() {

    override fun configure(states: StateMachineStateConfigurer<LoanStatus, LoanEvent>) {
        states.withStates()
            .states(EnumSet.allOf(LoanStatus::class.java))
            .initial(LoanStatus.NEW)
            .end(LoanStatus.APPROVED)
            .end(LoanStatus.REJECTED)
    }

    override fun configure(transitions: StateMachineTransitionConfigurer<LoanStatus, LoanEvent>) {
        transitions.withExternal()
            .source(LoanStatus.NEW).target(LoanStatus.USER_LOAN_PENDING)
            .event(LoanEvent.USER_LOAN)
            .action(userLoanAction)
            .and().withExternal()
            .source(LoanStatus.USER_LOAN_PENDING).target(LoanStatus.USER_LOAN_DONE)
            .event(LoanEvent.USER_LOAN_SUCCESS)
            .action(userLoanSuccessAction)
            .and().withExternal()
            .source(LoanStatus.USER_LOAN_PENDING).target(LoanStatus.REJECTED)
            .event(LoanEvent.INSUFFICIENT_USER_LOANS)
            .action(userLoanFailureAction)
            .and().withExternal()
            .source(LoanStatus.USER_LOAN_DONE).target(LoanStatus.RESERVE_BOOK_PENDING)
            .event(LoanEvent.RESERVE_BOOK)
            .action(reserveBookAction)
            .and().withExternal()
            .source(LoanStatus.RESERVE_BOOK_PENDING).target(LoanStatus.RESERVE_BOOK_DONE)
            .event(LoanEvent.RESERVE_BOOK_SUCCESS)
            .action(approveLoanAction)
            .and().withExternal()
            .source(LoanStatus.RESERVE_BOOK_PENDING).target(LoanStatus.RESERVE_BOOK_FAIL)
            .event(LoanEvent.INSUFFICIENT_BOOK_COPIES)
            .action(reserveBookFailureAction)
            .source(LoanStatus.RESERVE_BOOK_FAIL).target(LoanStatus.USER_DELOAN_DONE)
            .event(LoanEvent.USER_DELOAN)
            .action(userDeLoanAction)
            .source(LoanStatus.USER_DELOAN_DONE).target(LoanStatus.REJECTED)
            .event(LoanEvent.REJECT)
            .action(rejectAction)

    }

}
