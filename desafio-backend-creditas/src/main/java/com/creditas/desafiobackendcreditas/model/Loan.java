package com.creditas.desafiobackendcreditas.model;

import lombok.Generated;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Getter
public class Loan {

    @NotNull
    private final LoanType type;
    @NotNull
    private final Double taxes;

    public Loan(LoanType type, Double taxes) {
        Assert.notNull(type, "Loan type must not be null.");
        Assert.notNull(taxes, "Taxes must not be null.");
        this.type = type;
        this.taxes = taxes;
    }

    @Generated
    @Override
    public String toString() {
        return new StringJoiner(", ", Loan.class.getSimpleName() + "(", ")")
                .add("type=" + type)
                .add("taxes=" + taxes)
                .toString();
    }
}
