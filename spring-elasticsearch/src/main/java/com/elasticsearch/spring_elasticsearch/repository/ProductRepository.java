package com.elasticsearch.spring_elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elasticsearch.spring_elasticsearch.model.Product;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

}
