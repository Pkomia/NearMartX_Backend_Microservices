package com._Rbrothers.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com._Rbrothers.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
