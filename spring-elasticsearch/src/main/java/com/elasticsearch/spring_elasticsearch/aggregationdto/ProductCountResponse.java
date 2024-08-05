package com.elasticsearch.spring_elasticsearch.aggregationdto;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ProductCountResponse {
	private Map<String,Long> productsCount;
	
	public Map<String, Long> getProductsCount() {
		return productsCount;
	}

	public void setProductsCount(Map<String, Long> productsCount) {
		this.productsCount = productsCount;
	}

}
