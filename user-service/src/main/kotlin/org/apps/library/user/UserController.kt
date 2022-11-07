package org.apps.library.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class UserController {

    @GetMapping("/")
    @ResponseBody
    fun index(): String = "Hello World!"

}
