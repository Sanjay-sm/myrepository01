package com.ri.uploaddownloadfileapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStoragePojo.class
})
public class UploadDownloadFileAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadDownloadFileAppApplication.class, args);
	}

}
