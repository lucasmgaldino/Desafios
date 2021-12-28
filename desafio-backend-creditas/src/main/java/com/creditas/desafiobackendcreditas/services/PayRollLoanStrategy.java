package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.model.LoanType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PayRollLoanStrategy implements LoanStrategy {
    @Override
    public Optional<Loan> match(AnalysisRequest request) {
        if (request.getCustomer().hasIncomeGreaterThanOrEqualsTo(5000)) {
            return Optional.of(new Loan(LoanType.PAYROLL_LOAN, 2.0));
        }
        return Optional.empty();
    }
}
