package com.elasticsearch.spring_elasticsearch.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.elasticsearch.spring_elasticsearch.model.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkRequest.Builder;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class ElasticSearchRepo {
	
	@Autowired
	private ElasticsearchClient client;
//	@Autowired
//	private RestHighLevelClient client2;
	private final String indexName = "products";
	
	public String createOrUpdateProduct(Product product) throws ElasticsearchException, IOException {
		IndexResponse response = client.index(i -> i.index(indexName)
		                   .id(product.getId())
		                   .document(product));
		if(response.result().name().equals("Created")) {
			return new StringBuilder("Document has been Successfully created").toString();
		}
		else if(response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been updated successfully").toString();
		}
		 return new StringBuilder("Internal server Error").toString();	
		
	}
	
	
	public Product getDocumentById(String productId) throws ElasticsearchException, IOException {
		 GetResponse<Product> response = client.get(i -> i.index(indexName).id(productId), Product.class);
		 Product product = null;
		 if(response.found()) {
			  product = response.source();
			  System.out.println("Product name:" + product.getName());
		 }
		 return product;
	}
	
	
	public String deleteDocumentById(String productId) throws ElasticsearchException, IOException {
		DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId));
		DeleteResponse response = client.delete(request);
		
		if(Objects.nonNull(response.result()) && !response.result().name().equals("NotFound")) {
			return new StringBuilder("Product with id : " + response.id() + " has been deleted.").toString();
		}
		System.out.println("product not found");
		return new StringBuilder("Product with id : " + response.id() + "does not exist").toString();
		
	}
	
	
	public List<Product> searchAllProducts() throws ElasticsearchException, IOException{
		SearchRequest request = SearchRequest.of(s -> s.index(indexName));
		SearchResponse<Product> response = client.search(request, Product.class);
		
		List<Hit<Product>> hits = response.hits().hits();
		
		List<Product> products = new ArrayList<Product>();
		for(Hit object:hits) {
			System.out.println((Product)object.source());
			products.add((Product)object.source());
		}
		return products;
	}
	
	
	public String bulkSave(List<Product> products) throws ElasticsearchException, IOException {
		BulkRequest.Builder br = new BulkRequest.Builder();
		products.stream().forEach(product -> br.operations(operation -> operation
				                                           .index(i -> i.index(indexName)
				                                                          .id(product.getId())
				                                                          .document(product)
				                                            )));
		BulkResponse response = client.bulk(br.build());
		if(response.errors()) {
			return new StringBuffer("Bulk has errors").toString();
		}
		else {
			return new StringBuffer("Bulk save success").toString();
		}
	}
   
}
