package org.apps.library.commons.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * JSON Kotlinkx deserialization/serialization library. See <a href="https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md">kotlinx.serialization</a>
 */
class JsonUtils {

    companion object Methods {

        /**
         * Kotlinkx object to decode. Properties ignoreUnknownKeys = true
         *
         * @param T the type of the object to return
         * @property json to decode in a T object
         * @return object of T type
         */
        inline fun <reified T> decodeFromString(json: String): T {
            return Json.decodeFromString(json)
        }

        /**
         * Kotlinkx object serialization. Properties encodeDefaults = true
         *
         * @param T the type of the object to return
         * @property T object to encode into a JSON
         * @return json
         */
        inline fun <reified T> encodeToString(obj: T): String {
            return Json.encodeToString(obj)
        }
    }
}
