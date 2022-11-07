package org.apps.library.loan.loan

import org.springframework.data.jpa.repository.JpaRepository


interface LoanRepository : JpaRepository<Loan, Long>
