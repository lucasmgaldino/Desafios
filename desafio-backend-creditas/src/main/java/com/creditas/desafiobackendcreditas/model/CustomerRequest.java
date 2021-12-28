package com.creditas.desafiobackendcreditas.model;

import lombok.Generated;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.StringJoiner;

@Getter
public class CustomerRequest {

    @NotBlank
    private final String name;
    @NotBlank
    private final String cpf;
    @NotNull
    @Positive
    private final Integer age;
    @NotBlank
    private final String location;
    @NotNull
    @Positive
    private final BigDecimal income;

    public CustomerRequest(@NotBlank String name, @NotBlank String cpf, @NotNull @Positive Integer age,
                           @NotBlank String location,
                           @NotNull @Positive BigDecimal income) {
        Assert.hasText(name, "Should have a valid name.");
        Assert.hasText(cpf, "Should have a valid CPF.");
        Assert.hasText(location, "Should have a valid location.");
        Assert.notNull(income, "Should have an income.");
        Assert.isTrue(income.signum() >= 0, "Income must be positive.");
        this.name = name;
        this.cpf = cpf;
        this.age = age;
        this.location = location;
        this.income = income;
    }

    public boolean hasIncomeLessThanOrEqualsTo(double anIncome) {
        return this.income.compareTo(new BigDecimal(anIncome)) <= 0;
    }

    public boolean hasIncomeGreaterThan(double anIncome) {
        return this.income.compareTo(new BigDecimal(anIncome)) > 0;
    }

    public boolean hasIncomeLessThan(double anIncome) {
        return this.income.compareTo(new BigDecimal(anIncome)) < 0;
    }

    public boolean hasIncomeGreaterThanOrEqualsTo(double anIncome) {
        return this.income.compareTo(new BigDecimal(anIncome)) >= 0;
    }

    public boolean isUnderTheAgeOf(int anAge) {
        return this.age < anAge;
    }

    public boolean isLocationIn(String aLocation) {
        return this.location.equalsIgnoreCase(aLocation);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", CustomerRequest.class.getSimpleName() + "(", ")")
                .add("name='" + name + "'")
                .add("cpf='" + cpf + "'")
                .add("age=" + age)
                .add("location='" + location + "'")
                .add("income=" + income)
                .toString();
    }
}
