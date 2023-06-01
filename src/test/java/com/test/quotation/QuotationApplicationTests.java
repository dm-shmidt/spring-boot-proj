package com.test.quotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.quotation.model.dto.CustomerDto;
import com.test.quotation.model.dto.QuotationDto;
import com.test.quotation.model.dto.SubscriptionDto;
import com.test.quotation.model.request.IdRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuotationApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private MvcResult mvcResult;

    @Test
    @Order(1)
    void getSubscriptionAndCheckNestedObjectsTest() throws Exception {
        String uri = "/subscription/id";
        String content = objectMapper.writeValueAsString(new IdRequest(1L));
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        SubscriptionDto subscriptionDto = objectMapper.readValue(json, SubscriptionDto.class);

        assertEquals(1L, subscriptionDto.id());
        assertEquals(1L, subscriptionDto.quotation().id());
        assertEquals(1L, subscriptionDto.quotation().customer().id());
    }

    @Test
    @Order(2)
    void getAllCustomersTest() throws Exception {
        String uri = "/customer";
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        TypeReference<List<CustomerDto>> customerListType = new TypeReference<>() {
        };
        List<CustomerDto> customerDtoList = objectMapper.readValue(json, customerListType);
        assertTrue(customerDtoList.size() > 0);
    }

    @Test
    @Order(3)
    void getAllQuotationsTest() throws Exception {
        String uri = "/quotation";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        TypeReference<List<QuotationDto>> quotationListType = new TypeReference<>() {
        };
        List<QuotationDto> quotationDtos = objectMapper.readValue(json, quotationListType);
        assertTrue(quotationDtos.size() > 0);
    }

    @Test
    @Order(4)
    void getCustomerByIdTest() throws Exception {
        String uri = "/customer/id";
        String content = objectMapper.writeValueAsString(new IdRequest(1L));

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDto customerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDto.class);

        assertEquals("John", customerDto.firstName());
    }

    @Test
    @Order(5)
    void getQuotationByIdTest() throws Exception {
        String uri = "/quotation/id";
        String content = objectMapper.writeValueAsString(new IdRequest(1L));

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        QuotationDto quotationDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), QuotationDto.class);
        assertEquals(1000, quotationDto.insuredAmount());
    }

    @Test
    @Order(6)
    void getSubscriptionByIdTest() throws Exception {
        String uri = "/subscription/id";
        String content = objectMapper.writeValueAsString(new IdRequest(1L));

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubscriptionDto subscriptionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubscriptionDto.class);
        assertEquals("2023-01-21", subscriptionDto.startDate().toString());
    }

    @Test
    @Order(7)
    void addCustomerTest() throws Exception {
        String uri = "/customer";
        String content = """
                {
                    "first_name": "Sally",
                    "last_name": "Trueman",
                    "middle_name": "",
                    "email": "b.trueman@good.com",
                    "phone_number": "8-987-234-123",
                    "birth_date": "2000-08-09"
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDto customerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDto.class);
        assertEquals("Sally", customerDto.firstName());
    }

    @Test
    @Order(8)
    void addQuotationTest() throws Exception {
        String uri = "/quotation";
        String content = """
                {
                    "beginning_of_insurance": "2003-01-01",
                    "insured_amount": 23425,
                    "date_of_signing_mortgage": "2001-08-20"
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        QuotationDto quotationDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), QuotationDto.class);
        assertEquals(23425, quotationDto.insuredAmount());
    }

    @Test
    @Order(9)
    void addSubscriptionTest() throws Exception {
        String uri = "/subscription";
        String content = """
                {
                    "start_date": "2010-02-15",
                    "valid_until": "2030-10-10"
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubscriptionDto subscriptionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubscriptionDto.class);
        assertEquals("2010-02-15", subscriptionDto.startDate().toString());
    }

    @Test
    @Order(10)
    void patchCustomerTest() throws Exception {
        String uri = "/customer";
        String content = """
                {
                    "id": 1,
                    "first_name": "Tony",
                    "email": "t.strong@good.com",
                    "last_name": "Strong"
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDto customerDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDto.class);
        assertEquals("Tony", customerDto.firstName());
    }

    @Test
    @Order(11)
    void patchQuotationTest() throws Exception {
        String uri = "/quotation";
        String content = """
                {
                    "id": 1,
                    "beginning_of_insurance": "2010-11-20",
                    "insured_amount": 999898,
                    "customer": {
                        "id": 1,
                        "first_name": "Greg"
                    }
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        QuotationDto quotationDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), QuotationDto.class);
        assertEquals(999898, quotationDto.insuredAmount());
        assertEquals("Greg", quotationDto.customer().firstName());
    }

    @Test
    @Order(12)
    void patchSubscriptionTest() throws Exception {
        String uri = "/subscription";
        String content = """
                {
                    "id": 1,
                    "start_date": "2003-10-27",
                    "quotation": {
                        "beginning_of_insurance": "2010-01-31",
                        "customer": {
                            "first_name": "Abram"
                        }
                    }
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubscriptionDto subscriptionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubscriptionDto.class);
        assertEquals("2003-10-27", subscriptionDto.startDate().toString());
        assertEquals("2010-01-31", subscriptionDto.quotation().beginningOfInsurance().toString());
        assertEquals("Abram", subscriptionDto.quotation().customer().firstName());
    }

    @Test
    @Order(13)
    void attachCustomerToQuotationTest() throws Exception {
        String uri = "/quotation/attach_customer";
        String content = """
                {
                    "parent_id": 2,
                    "child_id": 8
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        QuotationDto quotationDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), QuotationDto.class);
        assertEquals(23425, quotationDto.insuredAmount());
        assertEquals("Sally", quotationDto.customer().firstName());
    }

    @Test
    @Order(14)
    void attachQuotationToSubscriptionTest() throws Exception {
        String uri = "/subscription/attach_quotation";
        String content = """
                {
                    "parent_id": 2,
                    "child_id": 2
                }""";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        SubscriptionDto subscriptionDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubscriptionDto.class);
        assertEquals("2010-02-15", subscriptionDto.startDate().toString());
        assertEquals(23425, subscriptionDto.quotation().insuredAmount());
        assertEquals("Sally", subscriptionDto.quotation().customer().firstName());
    }
}