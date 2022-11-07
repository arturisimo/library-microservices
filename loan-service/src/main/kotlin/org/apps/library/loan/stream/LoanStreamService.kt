package org.apps.library.loan.stream

import org.apps.library.commons.utils.JsonUtils
import org.apps.library.loan.sm.services.StateMachineService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.function.Consumer


@Service
class LoanStreamService(@Autowired val loanManager: StateMachineService) {

    val logger: Logger = LoggerFactory.getLogger("info")

    @Bean
    fun consumerUserLoanResponse(): Consumer<String> {
        return Consumer<String> {
                response -> loanManager.processUserLoan(JsonUtils.decodeFromString(response))
        }
    }

    @Bean
    fun consumerUserDeLoanResponse(): Consumer<String> {
        return Consumer<String> {
                response -> loanManager.processUserDeloan(JsonUtils.decodeFromString(response))
        }
    }

    @Bean
    fun consumerBookReserveResponse(): Consumer<String> {
        return Consumer<String> {
            response -> loanManager.processBookReserve(JsonUtils.decodeFromString(response))
        }
    }

}
