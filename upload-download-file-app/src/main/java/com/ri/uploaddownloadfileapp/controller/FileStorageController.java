package com.ri.uploaddownloadfileapp.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ri.uploaddownloadfileapp.UploadFileResponse;
import com.ri.uploaddownloadfileapp.service.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class FileStorageController {
  private FileStorageService service;
  
  public FileStorageController(FileStorageService service) {
	this.service = service;
}
  @PostMapping("/upload-single-file")
  public UploadFileResponse uploadSingleFile(@RequestParam("file") MultipartFile file) {
      String fileName = service.storeFile(file);

      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/download-file/")
              .path(fileName)
              .toUriString();

      return new UploadFileResponse(fileName, fileDownloadUri,
              file.getContentType(), file.getSize());
  }
  
  @PostMapping("/upload-multiple-files")
  public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
      return Arrays.asList(files)
              .stream()
              .map(file -> uploadSingleFile(file))  
              .collect(Collectors.toList());
  }
  
  @GetMapping("/download-file/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
      // Load file as Resource
      Resource resource = service.loadFileAsResource(fileName);

      // Try to determine file's content type
      String contentType = null;
      try {
          contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
          System.out.print("Could not determine file type.");
      }

      // Fallback to the default content type if type could not be determined
      if(contentType == null) {
          contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
              .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
              .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, 
                  "attachment; filename=\"" + resource.getFilename() + "\"")
              .body(resource);
  }
}
