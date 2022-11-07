package org.apps.library.loan.model

import kotlinx.serialization.Serializable

class Constants {
    companion object {
        enum class RejectionReason {
            INSUFFICIENT_BOOK_COPIES, INSUFFICIENT_USER_LOANS
        }

        enum class LoanEvent {
            USER_LOAN, USER_DELOAN, USER_LOAN_SUCCESS, INSUFFICIENT_USER_LOANS, RESERVE_BOOK, RESERVE_BOOK_SUCCESS, INSUFFICIENT_BOOK_COPIES, APPROVE, REJECT
        }

        const val LOAN_ID_HEADER: String = "LOAN_ID_HEADER"
    }
}
