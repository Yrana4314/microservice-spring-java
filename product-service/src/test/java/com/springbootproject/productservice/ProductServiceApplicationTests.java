package com.springbootproject.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootproject.productservice.model.Product;
import com.springbootproject.productservice.model.dto.ProductRequest;
import com.springbootproject.productservice.model.dto.ProductResponse;
import com.springbootproject.productservice.repository.ProductRepository;
import com.springbootproject.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	//Create MongodbCOntainer object
	@Container //JUnit know it is mongodb container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper; //to convert Object to JSOn and vice-versa
	@Autowired
	private ProductRepository productRepository;	//Just to check the size of the database/repository

	@Autowired
	private ProductService productService;
	@DynamicPropertySource //writes application.properties automatically while running this application
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry){
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}


	@Test
	void shouldCreateProduct() throws Exception{
		//get a product request
		ProductRequest productRequest = getProductRequest();
		//Convert ProductRequest object into JSON-String
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
		.andExpect(status().isCreated());
		//To make sure the repository size is same as many data we inserted
		//assertEquals(1,productRepository.findAll().size());
	}

	//Create a method called getProductRequest method that generates a product
	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("iphone 13")
				.description("iphone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}


	@Test
	void shouldGetAllProduct() throws  Exception{
		//create 2 demo products and store them in a list to store
		Product product1 = new Product("AA421","iphone 14","iphone 14",BigDecimal.valueOf(1000));
		Product product2 = new Product("BB421","iphone 14 pro","iphone 14 pro ",BigDecimal.valueOf(1200));
		List<Product> products = List.of(product1,product2);

		//save it to db
		productRepository.saveAll(products);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/product")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assertEquals(2,productRepository.findAll().size());
		System.out.println(productRepository.findAll().size());


	}
	/*
	@Test
	void ShouldGetProductById() throws Exception{
		//Create a Demo project
		Product product = new Product("AA11","iphone 15","Ipgone 15 model",BigDecimal.valueOf(1500));
		//Map it to JSON String_Object
		String productRequestString = objectMapper.writeValueAsString(product);
		//save it to the repository
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/AA11")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString(productRequestString)));

	}
*/
}
