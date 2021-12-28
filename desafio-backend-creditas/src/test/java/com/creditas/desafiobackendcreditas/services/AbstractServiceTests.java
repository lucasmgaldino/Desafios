package com.creditas.desafiobackendcreditas.services;

import com.creditas.desafiobackendcreditas.model.AnalysisRequest;
import com.creditas.desafiobackendcreditas.model.CustomerRequest;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Provide;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractServiceTests {

    protected static final String LOCATION_RN = "RN";
    protected static final String LOCATION_SP = "SP";
    private final String[] locations = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT",
            "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "TO"};

    @Provide
    Arbitrary<String> locationsWithoutSP() {
        return Arbitraries.of(locations);
    }

    @Provide
    Arbitrary<String> locationsWithSP() {
        Set<String> setLocales = new HashSet<>(Arrays.asList(locations));
        setLocales.add(LOCATION_SP);
        return Arbitraries.of(setLocales);
    }

    AnalysisRequest createAnalysisRequest(int age, String location, BigDecimal income) {
        return new AnalysisRequest(new CustomerRequest("Dino da Silva Sauro", "123.456.789-10", age, location,
                income));
    }

}
