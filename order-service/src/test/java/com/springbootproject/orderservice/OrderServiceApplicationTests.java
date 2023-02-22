package com.springbootproject.orderservice;

import com.springbootproject.orderservice.model.OrderLineItems;
import com.springbootproject.orderservice.model.dto.OrderLineItemsDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootproject.orderservice.model.dto.OrderRequest;
import com.springbootproject.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Container
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");
	@Autowired
	private ObjectMapper objectMapper;//Convert Object into JSON_String and vice-versa
	@Autowired
	private MockMvc mockMvc;
  	@Autowired
	private OrderRepository orderRepository;

	@DynamicPropertySource
	static void registerMySQlProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username",postgreSQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password",postgreSQLContainer::getPassword);
	}
	@Test
	void shouldCreateOrder() throws Exception{
		//get a order request
		OrderRequest orderRequest = getOrderRequest();
		//Map it to JSON_String object
		String orderRequestString = objectMapper.writeValueAsString(orderRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1,orderRepository.findAll().size());
	}

	private OrderRequest getOrderRequest(){
		var orderLineItemsDTO = new OrderLineItemsDTO();
		//orderLineItemsDTO.setId(Long.valueOf(124531));
		orderLineItemsDTO.setSkuCode("iphone 14pro");
		orderLineItemsDTO.setPrice(BigDecimal.valueOf(120000));
		orderLineItemsDTO.setQuantity(100);

		List<OrderLineItemsDTO> bList = new ArrayList<>();
		bList.add(orderLineItemsDTO);
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderLineItemsDTOList(bList);
		return orderRequest;

	}

}
