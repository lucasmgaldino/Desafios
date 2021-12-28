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

public class CollateralizedLoanStrategyTests extends AbstractServiceTests {

    @Label("Do not grants loans to those with an income equal to or greater than 5000 and has over 29 y.o")
    @Property(tries = 500)
    void test1(@ForAll @IntRange(min = 30, max = 100) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @BigRange(min = "5000", max = "1000000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isEmpty());
    }

    @Label("Grants loans to those with an income equal to or greater than 5000 and has under 30 y.o")
    @Property(tries = 500)
    void test2(@ForAll @IntRange(min = 1, max = 29) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @BigRange(min = "5000", max = "1000000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanType.COLLATERALIZED_LOAN, optionalLoan.get().getType());
        Assertions.assertEquals(3.0, optionalLoan.get().getTaxes());
    }

    // *****************************************************************************************************************

    @Label("Do not grants loans to those with an income equal to or less than 3000 and has over 29 y.o")
    @Property(tries = 500)
    void test3(@ForAll @IntRange(min = 30, max = 100) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @Positive @BigRange(max = "3000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isEmpty());
    }

    @Label("Do not grants loans to those with an income equal to or less than 3000, has over 29 y.o and reside in SP")
    @Property(tries = 500)
    void test4(@ForAll @IntRange(min = 30, max = 100) int age,
               @ForAll @Positive @BigRange(max = "3000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CollateralizedLoanStrategyTests.LOCATION_SP, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isEmpty());
    }

    @Label("Grants loans to those with an income equal to or less than 3000, has under 30 y.o and reside in SP")
    @Property(tries = 500)
    void test5(@ForAll @IntRange(min = 1, max = 29) int age,
               @ForAll @Positive @BigRange(max = "3000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CollateralizedLoanStrategyTests.LOCATION_SP, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanType.COLLATERALIZED_LOAN, optionalLoan.get().getType());
        Assertions.assertEquals(3.0, optionalLoan.get().getTaxes());
    }

    // *****************************************************************************************************************

    @Label("Does not grant loans to people with income between 3000 and 5000 and does not reside in SP")
    @Property(tries = 500)
    void test6(@ForAll @IntRange(min = 10, max = 100) int age,
               @ForAll("locationsWithoutSP") String location,
               @ForAll @BigRange(min = "3000", max = "5000", minIncluded = false, maxIncluded = false) BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isEmpty());
    }

    @Label("Grant loans to people with income between 3000 and 5000 and reside in SP")
    @Property(tries = 500)
    void test7(@ForAll @IntRange(min = 10, max = 100) int age,
               @ForAll @BigRange(min = "3000", max = "5000", minIncluded = false, maxIncluded = false) BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CollateralizedLoanStrategyTests.LOCATION_SP, income);
        LoanStrategy strategy = new CollateralizedLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanType.COLLATERALIZED_LOAN, optionalLoan.get().getType());
        Assertions.assertEquals(3.0, optionalLoan.get().getTaxes());
    }

}
