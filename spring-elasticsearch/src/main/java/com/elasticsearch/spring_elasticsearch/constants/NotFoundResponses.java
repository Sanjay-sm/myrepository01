package com.elasticsearch.spring_elasticsearch.constants;

import org.springframework.stereotype.Component;

@Component
public class NotFoundResponses {
 public final static String NOT_EXIST = "Document Does Not Exist";
 public final static String NOT_MATCH = "no search results found.! please search with correct texts";
 public final static String OUT_OF_RANGE = "No Products found with this Price Range.!";
}
