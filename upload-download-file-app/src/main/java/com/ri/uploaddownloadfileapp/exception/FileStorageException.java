package com.ri.uploaddownloadfileapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;


public class FileStorageException extends RuntimeException {
  public FileStorageException(String message) {
       super(message);
}
  public FileStorageException(String message,  Throwable cause) {
	super(message,cause);
}
}
