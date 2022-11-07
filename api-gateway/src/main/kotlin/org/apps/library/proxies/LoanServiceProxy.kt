package org.apps.library.proxies

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import org.apps.library.commons.utils.JsonUtils
import org.apps.library.proxies.dto.LoanDetailDTO
import org.apps.library.proxies.dto.LoanResponseDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LoanServiceProxy(private val userServiceProxy: UserServiceProxy,
                       private val bookServiceProxy: BookServiceProxy,
                        @Value("\${api.loans.uri}") private val uriLoan: String,
                        @Value("\${api.loans.path}") private val pathLoan: String) {

    fun findLoanById(loanId: Long, fullInfo: Boolean): LoanDetailDTO {

        val response = connector("$uriLoan$pathLoan$loanId")
        val loan: LoanResponseDTO = JsonUtils.decodeFromString(response.body)
        with(loan) {
            val loanDetail = LoanDetailDTO(id, status)
            if(fullInfo) {
                loanDetail.user = userServiceProxy.findUserById(loan.idUser)
                loanDetail.book = bookServiceProxy.findBookById(loan.idBook)
            }
            return loanDetail
        }
    }

    fun connector(url:String): HttpResponse<String> = Unirest.get(url).asString()

}
