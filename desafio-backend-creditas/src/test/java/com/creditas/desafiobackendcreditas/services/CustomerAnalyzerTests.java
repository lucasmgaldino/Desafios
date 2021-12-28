package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.Loan;
import com.creditas.desafiobackendcreditas.model.LoanType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerAnalyzerTests extends AbstractServiceTests {

    private static CustomerAnalyzer customerAnalyzer;

    @BeforeAll
    static void setup() {
        LoanStrategy personalLoanStrategy = new PersonalLoanStrategy();
        LoanStrategy collateralizedLoanStrategy = new CollateralizedLoanStrategy();
        LoanStrategy payRollLoanStrategy = new PayRollLoanStrategy();
        Set<LoanStrategy> strategies = Stream.of(personalLoanStrategy, collateralizedLoanStrategy,
                payRollLoanStrategy).collect(Collectors.toSet());
        CustomerAnalyzerTests.customerAnalyzer = new CustomerAnalyzer(strategies);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void test0(Set<LoanStrategy> strategies) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new CustomerAnalyzer(strategies);
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

    @DisplayName("Less than 30 years old, not residing in SP and income below 5000.")
    @ParameterizedTest
    @ValueSource(doubles = {2999.99, 3000, 3000.01, 4000, 4999.99})
    void test1(double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(29, CustomerAnalyzerTests.LOCATION_RN,
                new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(1, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
        Assertions.assertEquals(4.0, optionalPersonalLoan.get().getTaxes());
    }

    @DisplayName("Less than 30 years old, residing in any location and income over 5000.")
    @ParameterizedTest
    @CsvSource({"RN, 5000", "SP, 5000", "RN, 5000.01", "SP, 5000.01", "RN, 6000", "SP, 6000", "RN, 1000000", "SP, " +
            "1000000"})
    void test2(String location, double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(29, location, new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(3, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Optional<Loan> optionalCollateralizedLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.COLLATERALIZED_LOAN)).findFirst();
        Optional<Loan> optionalPayrollLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PAYROLL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
        Assertions.assertTrue(optionalCollateralizedLoan.isPresent());
        Assertions.assertTrue(optionalPayrollLoan.isPresent());
    }

    @DisplayName("Greater or equal than 30 years old, residing in any location and income over 5000.")
    @ParameterizedTest
    @CsvSource({"30, RN, 5000", "31, SP, 5000", "30, SP, 5000.01", "31, RN, 5000.01"})
    void test3(int age, String location, double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(2, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Optional<Loan> optionalPayrollLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PAYROLL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
        Assertions.assertTrue(optionalPayrollLoan.isPresent());
    }

    @DisplayName("Less than 30 years old, residing in SP and income below 3000.")
    @ParameterizedTest
    @ValueSource(doubles = {3000, 2999.99, 2000, 100})
    void test4(double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(29, CustomerAnalyzerTests.LOCATION_SP, new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(2, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Optional<Loan> optionalCollateralizedLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.COLLATERALIZED_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
        Assertions.assertTrue(optionalCollateralizedLoan.isPresent());
    }

    @DisplayName("Less than 30 years old, not residing in SP and income below 3000.")
    @ParameterizedTest
    @ValueSource(doubles = {3000, 2999.99, 2000, 100})
    void test5(double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(29, CustomerAnalyzerTests.LOCATION_RN,
                new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(1, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
    }

    @DisplayName("Greater or equal than 30 years old, residing in SP and income below 3000.")
    @ParameterizedTest
    @CsvSource({"30, 3000", "30, 2999.99", "30, 2000", "30, 100", "31, 3000", "31, 2999.99", "31, 2000", "31, 100"})
    void test6(int age, double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CustomerAnalyzerTests.LOCATION_SP, new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(1, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
    }

    @DisplayName("Any age, not residing in SP and income between 3000 and 5000.")
    @ParameterizedTest
    @CsvSource({"29, 3000.01", "30, 3000.01", "31, 3000.01",
                "29, 4000.00", "30, 4000.00", "31, 4000.00",
                "29, 4999.99", "30, 4999.99", "31, 4999.99"})
    void test7(int age, double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CustomerAnalyzerTests.LOCATION_RN,
                new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(1, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
    }

    @DisplayName("Any age, residing in SP and income between 3000 and 5000.")
    @ParameterizedTest
    @CsvSource({"29, 3000.01", "30, 3000.01", "31, 3000.01",
                "29, 4000.00", "30, 4000.00", "31, 4000.00",
                "29, 4999.99", "30, 4999.99", "31, 4999.99"})
    void test8(int age, double income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, CustomerAnalyzerTests.LOCATION_SP, new BigDecimal(income));
        Set<Loan> loans = CustomerAnalyzerTests.customerAnalyzer.analyse(analysisRequest);
        Assertions.assertNotNull(loans);
        Assertions.assertEquals(2, loans.size());
        Optional<Loan> optionalPersonalLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
        Optional<Loan> optionalCollateralizedLoan =
                loans.stream().filter(l -> l.getType().equals(LoanType.COLLATERALIZED_LOAN)).findFirst();
        Assertions.assertTrue(optionalPersonalLoan.isPresent());
        Assertions.assertTrue(optionalCollateralizedLoan.isPresent());
    }

}
