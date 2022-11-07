package org.apps.library.loan.rest

import java.net.URI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.apps.library.loan.loan.Loan
import org.apps.library.loan.loan.LoanService
import org.apps.library.loan.model.LoanRequestDTO
import org.apps.library.loan.model.LoanResponseDTO
import org.apps.library.loan.sm.services.StateMachineService
import org.apps.library.loan.toDTO
import org.apps.library.loan.toEntity
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest
import javax.security.auth.callback.ConfirmationCallback
import javax.validation.Valid


@RestController
@RequestMapping(value = ["/api/v1/loans"], produces = [MediaType.APPLICATION_JSON_VALUE])
class LoanResource(val loanService: LoanService, val stateMachineService: StateMachineService) {

    @Operation(
        summary = """List of loans""",
        responses = [
            ApiResponse(
                responseCode = "200", description = "List of loans",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = LoanResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping
    fun loans(): ResponseEntity<List<LoanResponseDTO>> = ResponseEntity.ok(loanService.findAll())

    @Operation(
        summary = "loan by id",
        responses = [
            ApiResponse(
                responseCode = "200", description = "loan by id",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = LoanResponseDTO::class))
                ]
            )
        ]
    )
    @GetMapping(("/{id}"))
    fun loan(
        @Parameter(description = "loan id", example = "1")
        @PathVariable id: Long
    ): ResponseEntity<LoanResponseDTO> = ResponseEntity.ok(loanService.get(id))

    @Operation(
        summary = "create a new loan",
        responses = [
            ApiResponse(
                responseCode = "201", description = "new loan",
                content = [
                    Content(mediaType = "application/json", schema = Schema(implementation = LoanResponseDTO::class))
                ]
            )
        ]
    )
    @PostMapping
    fun createLoan(@RequestBody @Valid loanDTO: LoanRequestDTO): ResponseEntity<LoanResponseDTO> {
        val loan: Loan = loanService.save(loanDTO.toEntity())
        stateMachineService.newLoan(loan)
        val location: URI = fromCurrentRequest().path("/{id}").buildAndExpand(loan.id).toUri()
        return ResponseEntity.created(location).body(loan.toDTO())
    }

    @PutMapping("/{id}")
    fun updateLoan(
        @Parameter(description = "loan id", example = "1")
        @PathVariable id: Long,
        @RequestBody @Valid loanDTO: LoanRequestDTO
    ): ResponseEntity<LoanResponseDTO> {
        return ResponseEntity.ok().body(loanService.update(id, loanDTO))
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    fun deleteLoan(@Parameter(description = "loan id", example = "1") @PathVariable id: Long): ResponseEntity<Void> {
        loanService.delete(id)
        return ResponseEntity.noContent().build()
    }


}
