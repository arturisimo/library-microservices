package org.apps.library.proxies

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.apps.library.commons.dto.BookDTO
import org.apps.library.commons.utils.JsonUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
@Service
class BookServiceProxy(@Value("\${api.books.uri}") private val uriBook: String,
                       @Value("\${api.books.path}") private val pathBook: String) {


    fun findBookById(bookId: Long): BookDTO {
        val response = connector("$uriBook$pathBook$bookId")
        return JsonUtils.decodeFromString(response.body)
    }

    fun connector(url:String): HttpResponse<String> = Unirest.get(url).asString()

}
