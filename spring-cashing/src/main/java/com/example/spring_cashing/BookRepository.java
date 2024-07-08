package com.example.spring_cashing;

public interface BookRepository {
 Book getByIsbn(String isbn);
}
