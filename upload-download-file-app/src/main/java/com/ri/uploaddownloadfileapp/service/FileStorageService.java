package com.ri.uploaddownloadfileapp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ri.uploaddownloadfileapp.FileStoragePojo;
import com.ri.uploaddownloadfileapp.exception.FileStorageException;
import com.ri.uploaddownloadfileapp.exception.MentionedFileNotFoundException;

import jakarta.annotation.Resource;

@Service
@SuppressWarnings("unused")
public class FileStorageService {
  private final Path fileStorageLocation;
  
  @Autowired
  public FileStorageService(FileStoragePojo filePojo) {
	this.fileStorageLocation = Paths.get(filePojo.getUploadDir())
			                   .toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
			
		} catch (IOException e ) {
			throw new FileStorageException("Sorry could not create required Directory");
		}
}
  
  public String storeFile(MultipartFile file)  {
	  String fileName  = StringUtils.cleanPath(file.getOriginalFilename());
		  
		    try {
		    	if(fileName.contains("..")) {
					  throw new FileStorageException("Sorry !! file name contains invalid path sequence"+ fileName);
				  }

			     Path targetLoaction = fileStorageLocation.resolve(fileName);
				 Files.copy(file.getInputStream(), targetLoaction, StandardCopyOption.REPLACE_EXISTING);
				 return fileName;
			} catch (IOException  | FileStorageException e) {
				
				e.getMessage();
			}
		   return null;
		  
	  }
	 
  
  public org.springframework.core.io.Resource loadFileAsResource(String fileName) {
	  org.springframework.core.io.Resource resource = null;
	  try {
          Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
          resource = new UrlResource(filePath.toUri());
          
          if(resource.exists()) {
              return resource;  
          } 
          else {
              throw new MentionedFileNotFoundException("File not found " + fileName);
          }
         
      } 
	  catch (MalformedURLException  | MentionedFileNotFoundException e) {
          e.getMessage();
      }
	return null;
  }
  }

