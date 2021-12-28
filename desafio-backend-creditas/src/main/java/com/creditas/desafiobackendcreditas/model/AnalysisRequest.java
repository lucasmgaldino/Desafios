package com.creditas.desafiobackendcreditas.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Generated;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Getter
public class AnalysisRequest {

    @NotNull
    @Valid
    private final CustomerRequest customer;

    @JsonCreator
    public AnalysisRequest(CustomerRequest customer) {
        Assert.notNull(customer, "Should have a customer.");
        this.customer = customer;
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", AnalysisRequest.class.getSimpleName() + "(", ")")
                .add("customer=" + customer)
                .toString();
    }
}
