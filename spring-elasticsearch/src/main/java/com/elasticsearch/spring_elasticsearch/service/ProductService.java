package com.elasticsearch.spring_elasticsearch.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

import com.elasticsearch.spring_elasticsearch.model.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Service
public class ProductService {
	private ElasticsearchTemplate template;
	private ElasticsearchClient client;
	private RestHighLevelClient restClient;
	
	public ProductService(ElasticsearchTemplate template, ElasticsearchClient client, RestHighLevelClient restClient) {
		super();
		this.template = template;
		this.client = client;
		this.restClient = restClient;
	}

	@SuppressWarnings("unused")
	public List<Product> findProductsByName(String searchKeyword) {
		NativeQuery nativeQuery = NativeQuery.builder()
				.withQuery(q -> q.match(t -> t.field("description").query(searchKeyword).fuzziness("2"))).build();

		Criteria criteria = new Criteria("price").greaterThan(100.0).lessThan(500.0);
		CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);

		SearchHits<Product> hits = template.search(nativeQuery, Product.class);
		List<SearchHit<Product>> searchHits = hits.getSearchHits();
			return searchHits.stream().map(s -> s.getContent()).collect(Collectors.toList());
	}

	public List<String> getfecthSuggestionsByName(String name) {
		String keyName = name.toLowerCase();
		NativeQuery nativeQuery = NativeQuery.builder()
				.withQuery(q -> q.wildcard(w -> w.field("description").wildcard(keyName + "*"))).build();

		SearchHits<Product> hits = template.search(nativeQuery, Product.class);
		List<SearchHit<Product>> searchHits = hits.getSearchHits();
		List<String> list = searchHits.stream().map(s -> s.getContent().getDescription()).collect(Collectors.toList());

		return list;
	}

	public List<Product> multiMatch(String keyName) {
		NativeQuery nativeQuery = NativeQuery.builder()
				.withQuery(q -> q.multiMatch(m -> m.fields("description", "name").query(keyName).fuzziness("2")))
				.build();
		SearchHits<Product> result = template.search(nativeQuery, Product.class);
		List<SearchHit<Product>> searchHits = result.getSearchHits();
		List<Product> list = searchHits.stream().map(s -> s.getContent()).collect(Collectors.toList());
		return list;
	}

	public List<Product> getProductsByPriceRange(String minPrice, String maxPrice) {
		NativeQuery nativeQuery = NativeQuery.builder()
				.withQuery(q -> q.range(r -> r.field("price").from(minPrice).to(maxPrice))).build();

		List<SearchHit<Product>> searchHits = template.search(nativeQuery, Product.class).getSearchHits();
		List<Product> list = searchHits.stream().map(s -> s.getContent()).collect(Collectors.toList());

		return list;
	}

	public Map<String, Long> getProductCount() throws IOException  {
//		NativeQuery nativeQuery = NativeQuery.builder()
//		           .withAggregation("by_description", Aggregation.of(a -> a.terms(t -> t.field("description"))))
//		           .build();
//		
//		SearchHits<Product> searchHits = template.search(nativeQuery, Product.class);
//		
//		ElasticsearchAggregation aggregations = ((ElasticsearchAggregations)searchHits.getAggregations()).get("by_description");
//		List<StringTermsBucket> array = aggregations.aggregation().getAggregate().sterms().buckets().array();
//		
//		
//		Map<String, Long> map = new HashMap<String, Long>();
//		
//		for(StringTermsBucket bucket : array) {
//			String description = bucket.key().stringValue();
//			long docCount = bucket.docCount();
//			map.put(description,docCount);
//		}
//		           
//		return map;
		
		SearchRequest request = new SearchRequest("new-products");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders 
		                                                       .terms("by_description")
		                                                       .field("description.keyword").size(20);   
   
		sourceBuilder.query(QueryBuilders.matchAllQuery()).aggregation(aggregation);
		
		request.source(sourceBuilder);
		
		@SuppressWarnings("deprecation")
		SearchResponse response = restClient.search(request, RequestOptions.DEFAULT);
		Aggregations aggregations = response.getAggregations();
		Terms terms = aggregations.get("by_description");
	
		List<? extends Bucket> buckets = terms.getBuckets();
		Map<String, Long> map = new HashMap<String, Long>();
		for(Terms.Bucket bucket:buckets) {
			String key = bucket.getKeyAsString();
			long docCount = bucket.getDocCount();
			map.put(key, docCount);
			//System.out.println(key);
		}
		return map;
		
	}

}
