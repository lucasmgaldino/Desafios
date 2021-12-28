package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.model.LoanType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollateralizedLoanStrategy implements LoanStrategy {
    @Override
    public Optional<Loan> match(AnalysisRequest request) {
        final Optional<Loan> collateralizedLoan = Optional.of(new Loan(LoanType.COLLATERALIZED_LOAN, 3.0));
        boolean isUnder30AgeOld = request.getCustomer().isUnderTheAgeOf(30);
        boolean isSPLocation = request.getCustomer().isLocationIn("SP");
        if (isUnder30AgeOld && isSPLocation && request.getCustomer().hasIncomeLessThanOrEqualsTo(3000)) {
            return collateralizedLoan;
        }
        if (isSPLocation && request.getCustomer().hasIncomeGreaterThan(3000) && request.getCustomer().hasIncomeLessThan(5000)) {
            return collateralizedLoan;
        }
        if (isUnder30AgeOld && request.getCustomer().hasIncomeGreaterThanOrEqualsTo(5000)) {
            return collateralizedLoan;
        }
        return Optional.empty();
    }
}
