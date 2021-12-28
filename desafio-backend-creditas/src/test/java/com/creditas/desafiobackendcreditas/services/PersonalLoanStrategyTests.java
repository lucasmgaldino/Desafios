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

public class PersonalLoanStrategyTests extends AbstractServiceTests {

    @Label("Grants loans to any people.")
    @Property(tries = 500)
    void test1(@ForAll @Positive @IntRange(max = 100) int age,
               @ForAll("locationsWithSP") String location,
               @ForAll @BigRange(min = "100", max = "1000000") BigDecimal income) {
        AnalysisRequest analysisRequest = createAnalysisRequest(age, location, income);
        LoanStrategy strategy = new PersonalLoanStrategy();
        Optional<Loan> optionalLoan = strategy.match(analysisRequest);
        Assertions.assertTrue(optionalLoan.isPresent());
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, optionalLoan.get().getType());
        Assertions.assertEquals(4.0, optionalLoan.get().getTaxes());
    }

}
