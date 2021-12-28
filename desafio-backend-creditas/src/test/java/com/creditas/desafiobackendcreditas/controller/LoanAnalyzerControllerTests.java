package com.creditas.desafiobackendcreditas.controller;

import com.creditas.desafiobackendcreditas.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class LoanAnalyzerControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test1() throws Exception {
		String payload = "";
		this.mockMvc.perform(
						MockMvcRequestBuilders.post("/api/analyze")
								.contentType(MediaType.APPLICATION_JSON)
								.content(payload))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andDo(handler -> System.out.println(handler.getResponse().getContentAsString()));
	}

	@Test
	void test2() throws Exception {
		AnalysisRequest analysisRequest = createAnalysisRequest(29, "RN", new BigDecimal(3000));
		String payload = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(analysisRequest);
		System.out.println(payload);
		ResultActions resultActions = this.mockMvc.perform(
						MockMvcRequestBuilders.post("/api/analyze")
								.contentType(MediaType.APPLICATION_JSON)
								.content(payload))
				.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		AnalysisResponse response = this.objectMapper.readValue(contentAsString, AnalysisResponse.class);
		System.out.println(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
		Assertions.assertNotNull(response, "Must not be null");
		Assertions.assertEquals("Dino da Silva Sauro", response.getCustomer());
		Optional<Loan> optionalPersonalLoan =
				response.getLoans().stream().filter(l -> l.getType().equals(LoanType.PERSONAL_LOAN)).findFirst();
		Assertions.assertTrue(optionalPersonalLoan.isPresent());
	}

	private AnalysisRequest createAnalysisRequest(final int age, final String location, final BigDecimal income) {
		return new AnalysisRequest(new CustomerRequest("Dino da Silva Sauro", "762.646.350-14", age, location,
				income));
	}

}
