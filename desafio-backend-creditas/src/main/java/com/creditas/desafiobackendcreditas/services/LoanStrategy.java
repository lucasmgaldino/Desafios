package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;

import java.util.Optional;

public interface LoanStrategy {

    /**
     * Checks if the person is eligible for the loan, based on analysis of the request data.
     * @param request an {@link AnalysisRequest} representing the data for loan analysis.
     * @return an {@link Optional} of {@link Loan}
     */
    Optional<Loan> match(AnalysisRequest request);

}
