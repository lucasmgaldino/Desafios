package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.model.LoanType;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Optional;

public class PayRollLoanStrategyTests extends AbstractServiceTests {

    @Label("Do not grant loans to those who has income less than 5000.")
    @Property(tries = 500)
    void test1(@ForAll @Positive @IntRange(max = 100) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @Positive @BigRange(max = "5000", maxIncluded = false) BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new PayRollLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isEmpty());
    }

    @Label("Grants loans to those with an income equal to or greater than 5000.")
    @Property(tries = 500)
    void test2(@ForAll @Positive @IntRange(max = 100) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @BigRange(min = "5000", max = "1000000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new PayRollLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanType.PAYROLL_LOAN, optionalLoan.get().getType());
        Assertions.assertEquals(2.0, optionalLoan.get().getTaxes());
    }

}
