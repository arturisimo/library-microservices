package org.apps.library.user.stream

import org.apps.library.commons.dto.Constants.Companion.RejectionReason
import org.apps.library.commons.dto.LoanMessageDTO
import org.apps.library.commons.dto.UserMessageDTO
import org.apps.library.commons.utils.JsonUtils
import org.apps.library.user.exceptions.UserLoansException
import org.apps.library.user.exceptions.UserNotFoundException
import org.apps.library.user.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import java.util.function.Consumer
import java.util.function.Supplier


@Service
class UserStreamService(val userService: UserService) {

    val logger: Logger = LoggerFactory.getLogger("info")

    var processor: EmitterProcessor<String> = EmitterProcessor.create()
    var processorDeLoan: EmitterProcessor<String> = EmitterProcessor.create()

    @Bean
    fun consumerLoan(): Consumer<String> =
        Consumer<String> {
            request -> handleUserLoan(JsonUtils.decodeFromString(request))
        }

    fun handleUserLoan(request: LoanMessageDTO) {
        logger.info("$request")
        val responseLoan: UserMessageDTO = try {
            UserMessageDTO(request.id, userService.loan(request))
        } catch (unfe: UserNotFoundException) {
            logger.error(unfe.message)
            UserMessageDTO(request.id, rejectionReason = RejectionReason.NOT_FOUND_USER.name)
        } catch (ule: UserLoansException) {
            logger.error(ule.message)
            UserMessageDTO(request.id, rejectionReason = RejectionReason.INSUFFICIENT_USER_LOANS.name)
        }
        processor.onNext(JsonUtils.encodeToString(responseLoan))
    }

    @Bean
    fun consumerDeLoan(): Consumer<String> =
        Consumer<String> {
           request -> userDeLoan(JsonUtils.decodeFromString(request))
        }


    fun userDeLoan(request: LoanMessageDTO) {
        logger.info("$request")
        val responseDeLoan: UserMessageDTO = try {
            UserMessageDTO(request.id, userService.deloan(request))
        } catch (unfe: UserNotFoundException) {
            logger.error(unfe.message)
            UserMessageDTO(request.id, rejectionReason = RejectionReason.NOT_FOUND_USER.name)
        }
        processor.onNext(JsonUtils.encodeToString(responseDeLoan))
    }

    @Bean
    fun producerLoan(): Supplier<Flux<String>> =
        Supplier { this.processor }


    @Bean
    fun producerDeLoan(): Supplier<Flux<String>> =
        Supplier { this.processorDeLoan }


}
