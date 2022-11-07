package org.apps.library.book.book

import org.apps.library.book.exceptions.BookCopiesException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Book (

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var copies: Int = 1

) {
    fun reserve(){
        if (copies-1 < 0) throw BookCopiesException("Not enough copies for book $title")
            copies -= 1
    }

}
