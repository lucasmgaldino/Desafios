package com.creditas.desafiobackendcreditas.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

public class AnalysisRequestTests {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\n", "\t"})
    void test1(String name) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new AnalysisRequest(new CustomerRequest(name, "762.646.350-14", 29,
                    "SP", new BigDecimal(1000)));
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\n", "\t"})
    void test2(String cpf) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new AnalysisRequest(new CustomerRequest("Dino da Silva Sauro", cpf, 29,
                    "SP", new BigDecimal(1000)));
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\n", "\t"})
    void test3(String location) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new AnalysisRequest(new CustomerRequest("Dino da Silva Sauro", "762.646.350-14", 29, location, new BigDecimal(1000)));
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

}
