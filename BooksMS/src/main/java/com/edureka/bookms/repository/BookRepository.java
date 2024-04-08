package com.edureka.bookms.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edureka.bookms.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

	Book findByTitle(String title);

	int deleteByTitle(String bookTitle);

	Book findByIsbn(String isbn);
	

}
