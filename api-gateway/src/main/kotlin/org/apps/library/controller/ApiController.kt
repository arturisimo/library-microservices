package org.apps.library.controller

import org.apps.library.proxies.LoanServiceProxy
import org.apps.library.proxies.UserServiceProxy
import org.apps.library.proxies.dto.LoanDetailDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(private val loanServiceProxy: LoanServiceProxy,) {

    @GetMapping("/borrows/{id}")
    fun loans(@PathVariable id: Long, @RequestParam(value = "fullinfo") fullInfo: Boolean): ResponseEntity<LoanDetailDTO> = ResponseEntity.ok(loanServiceProxy.findLoanById(id, fullInfo))

}
