package org.apps.library.loan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class LoanApp

fun main(args: Array<String>) {
    runApplication<LoanApp>(*args)
}
