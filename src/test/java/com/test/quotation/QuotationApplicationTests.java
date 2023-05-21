package com.test.quotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.quotation.model.dto.SubscriptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuotationApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getSubscriptionAndCheckNestedObjects() throws Exception {
		String uri = "/subscription/1";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn();

		String json = mvcResult.getResponse().getContentAsString();
		SubscriptionDto subscriptionDto = objectMapper.readValue(json, SubscriptionDto.class);

		assertEquals(1L, subscriptionDto.id());
		assertEquals(1L, subscriptionDto.quotation().id());
		assertEquals(1L, subscriptionDto.quotation().customer().id());
	}
}
