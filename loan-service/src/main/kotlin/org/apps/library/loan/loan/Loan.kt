package org.apps.library.loan.loan

import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Loan (

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var idBook: Long,

    @Column(nullable = false)
    var idUser: Long,

    @Column(nullable = false)
    var status: LoanStatus = LoanStatus.NEW,

    ) {
}
