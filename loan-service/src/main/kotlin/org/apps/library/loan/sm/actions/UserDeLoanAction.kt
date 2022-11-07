package org.apps.library.loan.sm.actions

import org.apps.library.loan.model.Constants.Companion.LoanEvent
import org.apps.library.commons.dto.Constants.Companion.LoanStatus
import org.apps.library.commons.unwrap
import org.apps.library.commons.utils.JsonUtils
import org.apps.library.loan.loan.LoanRepository
import org.apps.library.loan.toDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.statemachine.StateContext
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import java.util.function.Supplier

class UserDeLoanAction(val loanRepository: LoanRepository): LibraryAction<LoanStatus, LoanEvent>() {

    val logger: Logger = LoggerFactory.getLogger("info")

    var processor: EmitterProcessor<String> = EmitterProcessor.create()

    override fun execute(context: StateContext<LoanStatus, LoanEvent>) {
        val loanId = getLoanId(context)
        loanRepository.findById(loanId).unwrap()?.let {
            logger.info("$loanId: ${context.event}: ${it.status} > ${context.target.id}")
            it.status = context.target.id
            val loan = loanRepository.save(it)
            loanRepository.flush()
            processor.onNext(JsonUtils.encodeToString(loan.toDTO()))
        } ?: run {
            logger.error("loan $loanId not found")
        }
    }

    @Bean
    fun sendUserDeLoanRequest(): Supplier<Flux<String>> =
        Supplier { this.processor }

}
