package org.apps.library.commons.dto

import kotlinx.serialization.Serializable


class Constants {
    companion object {

        @Serializable
        enum class LoanStatus {
            NEW, USER_LOAN_PENDING, USER_LOAN_DONE, USER_DELOAN_DONE, RESERVE_BOOK_PENDING, RESERVE_BOOK_DONE, RESERVE_BOOK_FAIL, APPROVED, REJECTED
        }

        @Serializable
        enum class RejectionReason {
            INSUFFICIENT_BOOK_COPIES, INSUFFICIENT_USER_LOANS, NOT_FOUND_USER, NOT_FOUND_BOOK
        }

    }
}
