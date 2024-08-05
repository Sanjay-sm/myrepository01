package com.elasticsearch.spring_elasticsearch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import  org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.spring_elasticsearch.constants.NotFoundResponses;
import com.elasticsearch.spring_elasticsearch.model.Product;
import com.elasticsearch.spring_elasticsearch.repository.ElasticSearchRepo;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@RestController
public class ElasticSearchController {

	@Autowired
	private ElasticSearchRepo repository;
	
	 @PostMapping("/createOrUpdateDocument")
	    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
	          String response = repository.createOrUpdateProduct(product);
	        return new ResponseEntity<>(response,HttpStatus.OK);
	    }
	 
	 

	    @GetMapping("/getDocument")
	    public ResponseEntity<Object> getDocumentById(@RequestParam String productId) throws IOException {
	       Product product =  repository.getDocumentById(productId);
	       if(product != null) {
	        return new ResponseEntity<>(product, HttpStatus.OK);
	       }
	       else  {
	          return new ResponseEntity<>(NotFoundResponses.NOT_EXIST,HttpStatus.OK);
	       }
	      
	    }
	    
	    @DeleteMapping("/deleteDocument")
	    public ResponseEntity<Object> deleteDocumentById(@RequestParam String productId) throws IOException {
	        String response =  repository.deleteDocumentById(productId);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    @GetMapping("/searchDocument")
	    public ResponseEntity<Object> searchAllDocument() throws IOException {
	        List<Product> products = repository.searchAllProducts();
	        return new ResponseEntity<>(products, HttpStatus.OK);
	    }
	    
	    @PostMapping("/bulk")
	    public ResponseEntity<String> bulk(@RequestBody List<Product> products) throws ElasticsearchException, IOException{
	    	String response = repository.bulkSave(products);
	    	return new ResponseEntity<String>(response, HttpStatus.OK);
	    }
}
