package org.apps.library.loan.sm.actions

import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.loan.loan.LoanRepository
import org.springframework.stereotype.Component

@Component
class UserLoanSuccessAction(loanRepository: LoanRepository): SaveStatusAction<LoanStatus, LoanEvent>(loanRepository)
