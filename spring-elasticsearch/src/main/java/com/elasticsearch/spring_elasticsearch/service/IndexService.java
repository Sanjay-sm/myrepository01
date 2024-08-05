package com.elasticsearch.spring_elasticsearch.service;

import java.io.IOException;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
	@Autowired
	private RestHighLevelClient client;
	
	public void createIndex() throws IOException {
        String indexName = "new-products";

        // Define the index with a custom analyzer
        String mappingJson = """
        {
            "settings": {
                "analysis": {
                    "analyzer": {
                        "keyword_analyzer": {
                            "type": "custom",
                            "tokenizer": "keyword"
                        }
                    }
                }
            }
        }
        """;
        Request request = new Request("PUT", "/" + indexName);
        request.setJsonEntity(mappingJson);

        Response response = client.getLowLevelClient().performRequest(request);
        System.out.println(response.getStatusLine());
        
	}
}
