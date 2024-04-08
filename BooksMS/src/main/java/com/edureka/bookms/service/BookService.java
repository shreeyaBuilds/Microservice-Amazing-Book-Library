package com.edureka.bookms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edureka.bookms.model.Book;
import com.edureka.bookms.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookrepository;
	
	public Book getBookbyTitle(String title) {
		Book book = bookrepository.findByTitle(title);
		if(book != null) {
			return book;
		}
		return null;
		
	}
	
	public Book getBookbyIsbn(String isbn) {
		Book book = bookrepository.findByIsbn(isbn);
		if(book != null) {
			return book;
		}
		return null;
		
	}
	
	public Book addBook(Book book) {
	
		return bookrepository.save(book);
		
	}
	
	
	public Book updateBook(Book book) throws Exception {
		if(book.getIsbn()!=null && bookrepository.findByIsbn(book.getIsbn())!=null) {
			book = bookrepository.save(book);
		} else {
			throw new Exception("Invalid book for update");
		}
		return book;
	}
	
	@Transactional
	public int deleteBookByTitle(String bookTitle) {
		return bookrepository.deleteByTitle(bookTitle);
	}
	
	
	

}
