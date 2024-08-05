package com.elasticsearch.spring_elasticsearch.dto;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.elasticsearch.spring_elasticsearch.model.Product;

@Component
public class ProductResponse {

	private List<Product> productsList;
	

	public List<Product> getProductList() {
		return productsList;
	}

	public void setProductList(List<Product> list) {
		this.productsList = list;
	}

	
	
	
}
