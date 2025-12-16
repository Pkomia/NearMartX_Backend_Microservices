package com._Rbrothers.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com._Rbrothers.inventory_service.model.Inventory;
import com._Rbrothers.inventory_service.repository.InventoryRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner LoadData(InventoryRepository inventoryRepository){
           
		return args-> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("i_phone_13");
			inventory.setQuantity(2);
            
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("i_phone_14");
			inventory1.setQuantity(3);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("i_phone_15");
			inventory2.setQuantity(1);

			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("i_phone_16");
			inventory3.setQuantity(2);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
		};
	}

}
