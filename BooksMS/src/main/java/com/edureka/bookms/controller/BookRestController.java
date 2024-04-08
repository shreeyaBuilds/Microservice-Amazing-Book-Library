package com.edureka.bookms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edureka.bookms.model.Book;
import com.edureka.bookms.service.BookService;

@RestController
@RequestMapping("book")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@GetMapping("/get/{title}")
	public ResponseEntity<Book> getBook(@PathVariable("title") String title) {
		Book book = bookService.getBookbyTitle(title);
		if( book != null)
			return new ResponseEntity<>(book, HttpStatus.OK);
		 return new  ResponseEntity<>(book,HttpStatus.BAD_REQUEST);
	}

	@PostMapping("save")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		bookService.addBook(book);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	
	@PostMapping("/update")
	public ResponseEntity<Book> updateBook(@RequestBody Book book) {
		try {
			bookService.updateBook(book);
		} catch (Exception e) {
			 return new  ResponseEntity<>(book,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(book, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{title}")
	public ResponseEntity<String> deleteBook(@PathVariable("title") String bookTitle){
		if(bookService.deleteBookByTitle(bookTitle)!=0) {
			return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		} else {
			return new  ResponseEntity<>("Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/update/bookIssued/{isbn}/{noOfbbooksIssued}")
	public ResponseEntity<Integer> bookIssued(@PathVariable("isbn") String isbn, @PathVariable("noOfbbooksIssued") int noOfbbooksIssued) {
		Book book = bookService.getBookbyIsbn(isbn);
		int availableBookCopies = book.getTotalCopies()-book.getIssuedCopies();
		if(availableBookCopies < noOfbbooksIssued) {
			return new ResponseEntity<>(availableBookCopies,HttpStatus.BAD_REQUEST);
		}
		book.setIssuedCopies(book.getIssuedCopies()+noOfbbooksIssued);
		try {
			bookService.updateBook(book);
		} catch (Exception e) {
			 return new  ResponseEntity<>(availableBookCopies,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(noOfbbooksIssued,HttpStatus.OK);
		
	}
	
	@PostMapping("/update/bookReturned/{isbn}/{noOfBooksReturned}")
	public ResponseEntity<Integer> bookReturned(@PathVariable("isbn") String isbn, @PathVariable("noOfBooksReturned") int noOfBooksReturned) {
		Book book = bookService.getBookbyIsbn(isbn);
		if((book.getIssuedCopies() - noOfBooksReturned) <0 ) {
			return new ResponseEntity<>(noOfBooksReturned,HttpStatus.BAD_REQUEST);
		}
		book.setIssuedCopies(book.getIssuedCopies() - noOfBooksReturned);
		try {
			bookService.updateBook(book);
		} catch (Exception e) {
			 return new  ResponseEntity<>(noOfBooksReturned,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(noOfBooksReturned,HttpStatus.OK);
		
	}
	
}
