package com.creditas.desafiobackendcreditas.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

public class CustomerRequestTests {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\n", "\t"})
    void test1(String name) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            new CustomerRequest(name, "762.646.350-14", 29,
                    "SP", new BigDecimal(1000));
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-100000000", "-0.01", "-50000"})
    void test2(Double value) {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
            BigDecimal income = null;
            if (value != null) {
                income = new BigDecimal(value);
            }
            new CustomerRequest("Dino da Silva Sauro", "762.646.350-14", 29, "RN", income);
        }, "Should have thrown an exception like 'IllegalArgumentException'");
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.MAX_VALUE, 0.01, 50000})
    void test3(Double value) {
        CustomerRequest customerRequest = new CustomerRequest("Dino da Silva Sauro", "762.646.350-14",
                  29, "RN", new BigDecimal(value));
        Assertions.assertNotNull(customerRequest);
        Assertions.assertEquals("Dino da Silva Sauro",customerRequest.getName());
        Assertions.assertEquals("762.646.350-14",customerRequest.getCpf());
        Assertions.assertEquals(29,customerRequest.getAge());
        Assertions.assertEquals("RN",customerRequest.getLocation());
        Assertions.assertEquals(new BigDecimal(value), customerRequest.getIncome());
    }

}
