package org.apps.library.book.stream

import org.apps.library.commons.dto.LoanMessageDTO
import org.apps.library.commons.dto.BookMessageDTO
import org.apps.library.commons.dto.Constants.Companion.RejectionReason
import org.apps.library.book.exceptions.BookNotFoundException
import org.apps.library.book.book.BookService
import org.apps.library.book.exceptions.BookCopiesException
import org.apps.library.commons.utils.JsonUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux
import java.util.function.Consumer
import java.util.function.Supplier


@Service
class BookStreamService(val bookService: BookService) {

    val logger: Logger = LoggerFactory.getLogger("info")

    var processor: EmitterProcessor<String> = EmitterProcessor.create()

    @Bean
    fun consumerReserveBook(): Consumer<String> =
        Consumer<String> {
                request -> bookReserve(JsonUtils.decodeFromString(request))
        }


    fun bookReserve(request: LoanMessageDTO) {
        logger.info("$request")
        val responseReserveBook: BookMessageDTO = try {
            BookMessageDTO(request.id, bookService.reserve(request))
        } catch (unfe: BookNotFoundException) {
            logger.error(unfe.message)
            BookMessageDTO(request.id, rejectionReason = RejectionReason.NOT_FOUND_BOOK.name)
        } catch (ule: BookCopiesException) {
            logger.error(ule.message)
            BookMessageDTO(request.id, rejectionReason = RejectionReason.INSUFFICIENT_BOOK_COPIES.name)
        }
        processor.onNext(JsonUtils.encodeToString(responseReserveBook))
    }

    @Bean
    fun producerReserveBook(): Supplier<Flux<String>> =
        Supplier { this.processor }


}
