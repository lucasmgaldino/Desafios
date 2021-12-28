package com.creditas.desafiobackendcreditas.model;

import lombok.Generated;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.StringJoiner;

@Getter
public class AnalysisResponse {

    @NotEmpty
    private final String customer;
    @NotEmpty
    private final Set<Loan> loans;

    public AnalysisResponse(String customer, Set<Loan> loans) {
        Assert.hasText(customer, "Should have a customer name.");
        Assert.notNull(loans, "Loans must not be null.");
        Assert.isTrue(loans.iterator().hasNext(), "Should have at least one loan.");
        this.customer = customer;
        this.loans = loans;
    }

    @Generated
    @Override
    public String toString() {
        return new StringJoiner(", ", AnalysisResponse.class.getSimpleName() + "(", ")")
                .add("customer='" + customer + "'")
                .add("loans=" + loans)
                .toString();
    }

}
