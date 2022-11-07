package org.apps.library.proxies

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.apps.library.commons.dto.UserDTO
import org.apps.library.commons.utils.JsonUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class UserServiceProxy(@Value("\${api.users.uri}") private val uriUser: String,
                       @Value("\${api.users.path}") private val pathUser: String) {

    fun findUserById(userId: Long): UserDTO {
        val response = connector("$uriUser$pathUser$userId")
        return JsonUtils.decodeFromString(response.body)
    }

    fun connector(url:String): HttpResponse<String> = Unirest.get(url).asString()
}
