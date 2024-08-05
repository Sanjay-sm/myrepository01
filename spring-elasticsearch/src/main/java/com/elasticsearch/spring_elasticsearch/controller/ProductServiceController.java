package com.elasticsearch.spring_elasticsearch.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.spring_elasticsearch.aggregationdto.ProductCountResponse;
import com.elasticsearch.spring_elasticsearch.constants.NotFoundResponses;
import com.elasticsearch.spring_elasticsearch.constants.NotSuccessResponces;
import com.elasticsearch.spring_elasticsearch.constants.SuccessResponses;
import com.elasticsearch.spring_elasticsearch.dto.ProductResponse;
import com.elasticsearch.spring_elasticsearch.model.Product;
import com.elasticsearch.spring_elasticsearch.repository.ProductRepository;
import com.elasticsearch.spring_elasticsearch.service.IndexService;
import com.elasticsearch.spring_elasticsearch.service.ProductService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@RestController
public class ProductServiceController {
	@Autowired
	private ProductService service;
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private ElasticsearchClient client;

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ProductResponse productResponse;
	@Autowired
	private ProductCountResponse productCountResponse;

	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Product  product) {
		// Product response = new Product();

		try {
			Optional<Product> result = repository.findById(product.getId());

			if (result.isPresent()) {
				return new ResponseEntity<Object>(SuccessResponses.exist, HttpStatus.ALREADY_REPORTED);

			}
			if (result.isEmpty()) {
				Product response = repository.save(product);
				if (response != null) {
					return new ResponseEntity<Object>(SuccessResponses.success, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(NotSuccessResponces.NOT_SUCCESS, HttpStatus.OK);
				}

			} else {
				return new ResponseEntity<Object>(NotSuccessResponces.NOT_SUCCESS, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// to get the products by it's description
	@GetMapping("/prouctsByName/{name}")
	public ResponseEntity<Object> getPrductsByName(@PathVariable String name){
		List<Product> products = service.findProductsByName(name);
		productResponse.setProductList(products);
		if(!products.isEmpty()) {
		 return new ResponseEntity<>(productResponse,HttpStatus.OK);
	}
		else {
			return new ResponseEntity<>(NotFoundResponses.NOT_MATCH,HttpStatus.OK);
		}

}
	// to get the sequence of suggestions for a particular description
	@GetMapping("/fetchSuggestions/{name}")
	public List<String> fetchSuggestions(@PathVariable String name){
		return service.getfecthSuggestionsByName(name);
	}
	
	//to get the products by giving multiple fields name
	@GetMapping("/multiMatch/{keyName}")
	public ResponseEntity<Object> multiMatchApi(@PathVariable String keyName){
		List<Product> result = service.multiMatch(keyName);
		productResponse.setProductList(result);
		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
	}
	
	// to get the products based on price range
	@GetMapping("/productsByPriceRange/{minPrice}/{maxPrice}")
	public ResponseEntity<Object> rangeQueryApi(@PathVariable String minPrice, @PathVariable String maxPrice){
		List<Product> result =  service.getProductsByPriceRange(minPrice,maxPrice);
		if(!result.isEmpty()) {
		productResponse.setProductList(result);
		return new ResponseEntity<Object>(productResponse, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(NotFoundResponses.OUT_OF_RANGE,HttpStatus.OK);
		}
	}
	
	// to get the no of products of description
	@GetMapping("/aggregation")
	public ResponseEntity<Object> aggregationApi() throws IOException{
		try {
			//indexService.createIndex();
			Map<String,Long> result =  service.getProductCount();
			productCountResponse.setProductsCount(result);
			return new ResponseEntity<Object>(productCountResponse,HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
