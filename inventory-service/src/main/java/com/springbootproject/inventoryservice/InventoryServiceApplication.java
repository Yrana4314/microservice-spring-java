package com.springbootproject.inventoryservice;

import com.springbootproject.inventoryservice.model.Inventory;
import com.springbootproject.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		//At the time of application running, it will create 2 objects and save it to databases
		return args ->{
			//Create two inventory object i.e. inventory1 and inventory2
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iphone 14");
			inventory1.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iphone 14 pro");
			inventory2.setQuantity(0);

			//save 2 inventory obj into database
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};

	}

}
