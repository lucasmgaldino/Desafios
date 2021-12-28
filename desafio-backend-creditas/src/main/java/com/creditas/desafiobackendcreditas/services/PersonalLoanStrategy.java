package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.model.LoanType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalLoanStrategy implements LoanStrategy {

    @Override
    public Optional<Loan> match(AnalysisRequest request) {
        return Optional.of(new Loan(LoanType.PERSONAL_LOAN, 4.0));
    }

}
