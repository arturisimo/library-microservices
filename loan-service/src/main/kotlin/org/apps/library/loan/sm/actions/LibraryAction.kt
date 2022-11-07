package org.apps.library.loan.sm.actions

import org.apps.library.loan.model.Constants
import org.springframework.statemachine.StateContext
import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.springframework.statemachine.action.Action

abstract class LibraryAction<T, U> : Action<LoanStatus, LoanEvent> {

    protected fun getLoanId(context: StateContext<LoanStatus, LoanEvent>) =
        (context.message.headers[Constants.LOAN_ID_HEADER] as String).toLong()

    abstract override fun execute(context: StateContext<LoanStatus, LoanEvent>)

}
