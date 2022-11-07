package org.apps.library.user.user

import org.apps.library.user.exceptions.UserLoansException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class User (

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var loans: Int = 6

) {
    fun loan() {
        loans -= 1
        if (loans < 0)
            throw UserLoansException("loans availables insufficient for user $id ")
    }

    fun deloan() {
        loans += 1
    }
}
