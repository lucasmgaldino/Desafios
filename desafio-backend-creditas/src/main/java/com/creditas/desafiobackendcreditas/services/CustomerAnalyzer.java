package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerAnalyzer {

    private final Set<LoanStrategy> strategies;

    public CustomerAnalyzer(Set<LoanStrategy> strategies) {
        Assert.notNull(strategies, "Strategies must be not null");
        Assert.isTrue(strategies.iterator().hasNext(), "Should have at least one strategy.");
        this.strategies = strategies;
    }

    /**
     * Analyzes a request and returns the set of loans that the person is entitled to.
     * @param request an {@link AnalysisRequest} representing the data for loan analysis.
     * @return an {@link Set} of {@link Loan}
     */
    public Set<Loan> analyse(AnalysisRequest request) {
        Assert.notNull(request, "Request must be not null");
        return this.strategies.stream().map(loanStrategy -> loanStrategy.match(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
