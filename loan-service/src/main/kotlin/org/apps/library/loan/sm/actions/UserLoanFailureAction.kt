package org.apps.library.loan.sm.actions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.commons.unwrap
import org.apps.library.loan.loan.LoanRepository
import org.springframework.statemachine.StateContext
import org.springframework.stereotype.Component

@Component
class UserLoanFailureAction(loanRepository: LoanRepository): SaveStatusAction<LoanStatus, LoanEvent>(loanRepository)



