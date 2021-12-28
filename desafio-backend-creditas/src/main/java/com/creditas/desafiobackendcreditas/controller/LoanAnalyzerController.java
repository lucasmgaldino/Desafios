package com.creditas.desafiobackendcreditas.controller;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.AnalysisResponse;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.services.CustomerAnalyzer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class LoanAnalyzerController {

    private final CustomerAnalyzer customerAnalyzer;

    public LoanAnalyzerController(CustomerAnalyzer aCustomerAnalyzer) {
        this.customerAnalyzer = aCustomerAnalyzer;
    }

    @Operation(summary = "Perform analysis for loan.",
            description = "Analyzes a people profile and returns the loans that the person is entitled to.", tags = {
            "LoanAnalyzer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnalysisResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json")
            })
    })
    @PostMapping("/api/analyze")
    public AnalysisResponse execute(@Parameter(description = "Data required for analysis", required = true) @RequestBody @Valid AnalysisRequest request) {
        Set<Loan> loans = this.customerAnalyzer.analyse(request);
        return new AnalysisResponse(request.getCustomer().getName(), loans);
    }

}
