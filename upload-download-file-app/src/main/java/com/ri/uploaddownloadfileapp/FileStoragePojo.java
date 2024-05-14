package com.ri.uploaddownloadfileapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
public class FileStoragePojo {
  private String uploadDir;
  
  public String getUploadDir() {
	return uploadDir;
}
  public void setUploadDir(String uploadDir) {
	this.uploadDir = uploadDir;
}
}
