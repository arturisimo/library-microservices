package org.apps.library.book

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class BookController {

    @GetMapping("/")
    @ResponseBody
    fun index(): String = "Hello World!"

}
