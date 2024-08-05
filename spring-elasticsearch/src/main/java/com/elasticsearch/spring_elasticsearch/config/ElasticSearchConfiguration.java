package com.elasticsearch.spring_elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.analysis.Analyzer;
import co.elastic.clients.elasticsearch._types.analysis.AnalyzerBuilders;
import co.elastic.clients.elasticsearch._types.analysis.Tokenizer;
import co.elastic.clients.elasticsearch._types.analysis.WhitespaceAnalyzer;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfiguration {

	
	@Bean
	public RestClient getRestClient() {
		return RestClient.builder(new HttpHost("localhost",9200)).build();
	}
	
	@Bean
	public ElasticsearchTransport getElasticSearchTransport() {
		return new RestClientTransport(getRestClient(), new JacksonJsonpMapper());
	}
	
	@Bean
	public ElasticsearchClient getElasticSearchClient() {
		return new ElasticsearchClient(getElasticSearchTransport());
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public RestHighLevelClient getHighRestClient() {
		RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200)));

        return client;
	}
	
	 
}
